/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;

import jdk.javadoc.internal.doclets.formats.html.Navigation.PageMode;
import jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyles;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.doclets.toolkit.util.PreviewAPIListBuilder;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.ContentBuilder;
import jdk.javadoc.internal.html.HtmlStyle;
import jdk.javadoc.internal.html.HtmlTree;
import jdk.javadoc.internal.html.Text;

/**
 * Generate File to list all the preview elements with the
 * appropriate links.
 */
public class PreviewListWriter extends SummaryListWriter<PreviewAPIListBuilder> {

    /**
     * Constructor.
     *
     * @param configuration the configuration for this doclet
     */
    public PreviewListWriter(HtmlConfiguration configuration) {
        super(configuration, DocPaths.PREVIEW_LIST, configuration.previewAPIListBuilder);
    }

    @Override
    protected PageMode getPageMode() {
        return PageMode.PREVIEW;
    }

    @Override
    protected String getDescription() {
        return "preview elements";
    }

    @Override
    protected Content getHeadContent() {
        return configuration.contents.previewAPI;
    }

    @Override
    protected String getTitleKey() {
        return "doclet.Window_Preview_List";
    }

    @Override
    protected void addContentSelectors(Content target) {
        Set<PreviewAPIListBuilder.JEP> jeps = builder.getJEPs();
        if (!jeps.isEmpty()) {
            int index = 1;
            target.add(HtmlTree.P(contents.getContent("doclet.Preview_API_Checkbox_Label")));
            Content list = HtmlTree.UL(HtmlStyles.previewFeatureList).addStyle(HtmlStyles.checkboxes);
            for (var jep : jeps) {
                String jepUrl = resources.getText("doclet.Preview_JEP_URL", String.valueOf(jep.number()));
                Content label = new ContentBuilder(Text.of(jep.number() + ": "))
                        .add(HtmlTree.A(jepUrl, Text.of(jep.title() + " (" + jep.status() + ")")));
                list.add(HtmlTree.LI(getCheckbox(label, String.valueOf(index++), "feature-")));
            }
            Content label = contents.getContent("doclet.Preview_API_Checkbox_Toggle_All");
            list.add(HtmlTree.LI(getCheckbox(label, ID_ALL, "feature-")));
            target.add(list);
        }
    }

    @Override
    protected void addComments(Element e, Content desc) {
        List<? extends DocTree> tags = utils.getFirstSentenceTrees(e);
        if (!tags.isEmpty()) {
            addPreviewComment(e, tags, desc);
        } else {
            desc.add(Text.EMPTY);
        }
    }

    @Override
    protected void addTableTabs(Table<Element> table, String headingKey) {
        table.setGridStyle(HtmlStyles.threeColumnSummary)
                .setDefaultTab(getTableCaption(headingKey))
                .setRenderTabs(false);
        for (PreviewAPIListBuilder.JEP jep : builder.getJEPs()) {
            table.addTab(Text.EMPTY, element -> jep == builder.getJEP(element));
        }
    }

    @Override
    protected Content getExtraContent(Element element) {
        PreviewAPIListBuilder.JEP jep = builder.getJEP(element);
        return jep == null ? Text.EMPTY : Text.of(jep.title());
    }

    @Override
    protected TableHeader getTableHeader(String headerKey) {
        return new TableHeader(
                contents.getContent(headerKey),
                Text.of("Preview Feature"),
                contents.descriptionLabel)
                .sortable(true, true, false); // Allow sorting by element name and feature
    }

    @Override
    protected HtmlStyle[] getColumnStyles() {
        return new HtmlStyle[]{ HtmlStyles.colSummaryItemName, HtmlStyles.colSecond, HtmlStyles.colLast };
    }
}
