/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;

import java.util.EnumSet;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.SummaryTree;

import jdk.javadoc.doclet.Taglet.Location;
import jdk.javadoc.internal.doclets.formats.html.HtmlConfiguration;
import jdk.javadoc.internal.html.Content;

/**
 * A taglet that represents the {@code {@summary}} tag.
 */
public class SummaryTaglet extends BaseTaglet {

    SummaryTaglet(HtmlConfiguration config) {
        super(config, DocTree.Kind.SUMMARY, true, EnumSet.allOf(Location.class));
    }

    @Override
    public Content getInlineTagOutput(Element holder, DocTree tag, TagletWriter tagletWriter) {
        return tagletWriter.commentTagsToOutput(holder, tag, ((SummaryTree)tag).getSummary(),
                tagletWriter.context.isFirstSentence);
    }
}
