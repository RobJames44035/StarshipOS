/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.toolkit.util;

import jdk.javadoc.internal.doclets.toolkit.BaseConfiguration;

import javax.lang.model.element.Element;

/**
 * Build list of all the restricted methods.
 */
public class RestrictedAPIListBuilder extends SummaryAPIListBuilder {


    /**
     * Constructor.
     *
     * @param configuration the current configuration of the doclet
     */
    public RestrictedAPIListBuilder(BaseConfiguration configuration) {
        super(configuration);
        buildSummaryAPIInfo();
    }

    @Override
    protected boolean belongsToSummary(Element element) {
        return utils.isRestrictedAPI(element);
    }
}
