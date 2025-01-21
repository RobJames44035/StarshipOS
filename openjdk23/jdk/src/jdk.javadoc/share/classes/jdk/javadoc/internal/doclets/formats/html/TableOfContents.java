/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.javadoc.internal.doclets.formats.html;

import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.Entity;
import jdk.javadoc.internal.html.HtmlAttr;
import jdk.javadoc.internal.html.HtmlId;
import jdk.javadoc.internal.html.HtmlTag;
import jdk.javadoc.internal.html.HtmlTree;
import jdk.javadoc.internal.html.ListBuilder;
import jdk.javadoc.internal.html.Text;

/**
 * A class used by various {@link HtmlDocletWriter} subclasses to build tables of contents.
 */
public class TableOfContents {
    private final ListBuilder listBuilder;
    private final HtmlDocletWriter writer;

    /**
     * Constructor
     * @param writer the writer
     */
    public TableOfContents(HtmlDocletWriter writer) {
        this.writer = writer;
        listBuilder = new ListBuilder(HtmlTree.OL(HtmlStyles.tocList));
    }

    /**
     * Adds a link to the table of contents.
     * @param id the link fragment
     * @param label the link label
     * @return this object
     */
    public TableOfContents addLink(HtmlId id, Content label) {
        listBuilder.add(writer.links.createLink(id, label).put(HtmlAttr.TABINDEX, "0"));
        return this;
    }

    /**
     * Adds a new nested list to add new items to.
     */
    public void pushNestedList() {
        listBuilder.pushNestedList(HtmlTree.OL(HtmlStyles.tocList));
    }

    /**
     * Closes the current nested list and go back to the parent list.
     */
    public void popNestedList() {
        listBuilder.popNestedList();
    }

    /**
     * Returns a content object containing the table of contents, consisting
     * of a header and the contents list itself. If the contents list is empty,
     * an empty content object is returned.
     *
     * @param hasFilterInput whether to add a filter text input
     * @return a content object
     */
    protected Content toContent(boolean hasFilterInput) {
        if (listBuilder.isEmpty()) {
            return Text.EMPTY;
        }
        var content = HtmlTree.NAV()
                .setStyle(HtmlStyles.toc)
                .put(HtmlAttr.ARIA_LABEL, writer.resources.getText("doclet.table_of_contents"));
        var header = HtmlTree.DIV(HtmlStyles.tocHeader, writer.contents.contentsHeading);
        if (hasFilterInput) {
            header.add(Entity.NO_BREAK_SPACE)
                    .add(HtmlTree.INPUT(HtmlAttr.InputType.TEXT, HtmlStyles.filterInput)
                            .put(HtmlAttr.PLACEHOLDER, writer.resources.getText("doclet.filter_label"))
                            .put(HtmlAttr.ARIA_LABEL, writer.resources.getText("doclet.filter_table_of_contents"))
                            .put(HtmlAttr.AUTOCOMPLETE, "off"))
                    .add(HtmlTree.INPUT(HtmlAttr.InputType.RESET, HtmlStyles.resetFilter)
                            .put(HtmlAttr.VALUE, writer.resources.getText("doclet.filter_reset")));
        }
        content.add(header);
        content.add(HtmlTree.BUTTON(HtmlStyles.hideSidebar)
                .add(HtmlTree.SPAN(writer.contents.hideSidebar).add(Entity.NO_BREAK_SPACE))
                .add(Entity.LEFT_POINTING_ANGLE));
        content.add(HtmlTree.BUTTON(HtmlStyles.showSidebar)
                .add(Entity.RIGHT_POINTING_ANGLE)
                .add(HtmlTree.SPAN(Entity.NO_BREAK_SPACE).add(writer.contents.showSidebar)));
        return content.add(listBuilder);
    }

}
