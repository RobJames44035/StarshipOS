/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html;

import javax.lang.model.element.Element;

import jdk.javadoc.internal.doclets.formats.html.Navigation.PageMode;
import jdk.javadoc.internal.doclets.toolkit.util.DocPaths;
import jdk.javadoc.internal.doclets.toolkit.util.IndexItem;
import jdk.javadoc.internal.doclets.toolkit.util.RestrictedAPIListBuilder;
import jdk.javadoc.internal.html.Content;

/**
 * Generate File to list all the restricted methods with the
 * appropriate links.
 */
public class RestrictedListWriter extends SummaryListWriter<RestrictedAPIListBuilder> {

    /**
     * Constructor.
     *
     * @param configuration the configuration for this doclet
     */
    public RestrictedListWriter(HtmlConfiguration configuration) {
        super(configuration, DocPaths.RESTRICTED_LIST, configuration.restrictedAPIListBuilder);
        if (configuration.restrictedAPIListBuilder != null) {
            configuration.indexBuilder.add(IndexItem.of(IndexItem.Category.TAGS,
                    resources.getText("doclet.Restricted_Methods"), path));
        }
    }

    @Override
    protected PageMode getPageMode() {
        return PageMode.RESTRICTED;
    }

    @Override
    protected String getDescription() {
        return "restricted methods";
    }

    @Override
    protected boolean showContentsList() {
        // This list contains only methods, no need to show list of element kinds.
        return false;
    }

    @Override
    protected Content getHeadContent() {
        return configuration.contents.restrictedMethods;
    }

    @Override
    protected String getTitleKey() {
        return "doclet.Window_Restricted_List";
    }

    @Override
    protected void addComments(Element e, Content desc) {
        addSummaryComment(e, desc);
    }
}
