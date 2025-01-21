/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.toolkit.util;

import com.sun.source.doctree.SinceTree;

import jdk.javadoc.internal.doclets.toolkit.BaseConfiguration;

import javax.lang.model.element.Element;
import java.util.List;


import static com.sun.source.doctree.DocTree.Kind.SINCE;

/**
 * Build list of all the packages, classes, constructors, fields and methods
 * that were added in one of the releases specified by the {@code --since}
 * option. The release names must exactly match the names used in the javadoc
 * {@code @since} tags of the respective elements.
 */
public class NewAPIBuilder extends SummaryAPIListBuilder {

    public final List<String> releases;

    public NewAPIBuilder(BaseConfiguration configuration, List<String> releases) {
        super(configuration);
        this.releases = releases;
        buildSummaryAPIInfo();
    }

    @Override
    protected boolean belongsToSummary(Element element) {
        if (!utils.hasDocCommentTree(element)) {
            return false;
        }
        var sinceTrees = utils.getBlockTags(element, SINCE, SinceTree.class);
        if (sinceTrees.isEmpty()) {
            return false;
        }

        // assumes a simple string value with no formatting
        return sinceTrees.stream()
                .anyMatch(tree -> releases.contains(tree.getBody().getFirst().toString()));
    }
}
