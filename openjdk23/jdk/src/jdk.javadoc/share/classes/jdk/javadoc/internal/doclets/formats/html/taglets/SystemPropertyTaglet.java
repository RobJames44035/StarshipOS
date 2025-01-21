/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;

import java.util.EnumSet;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;
import com.sun.source.doctree.SystemPropertyTree;

import jdk.javadoc.doclet.Taglet;
import jdk.javadoc.internal.doclets.formats.html.HtmlConfiguration;
import jdk.javadoc.internal.html.Content;
import jdk.javadoc.internal.html.HtmlTree;

/**
 * A taglet that represents the {@code @systemProperty} tag.
 */
public class SystemPropertyTaglet extends BaseTaglet {

    SystemPropertyTaglet(HtmlConfiguration config) {
        super(config, DocTree.Kind.SYSTEM_PROPERTY, true, EnumSet.allOf(Taglet.Location.class));
    }

    @Override
    public Content getInlineTagOutput(Element element, DocTree tag, TagletWriter tagletWriter) {
        this.tagletWriter = tagletWriter;
        return systemPropertyTagOutput(element, (SystemPropertyTree) tag);
    }

    /**
     * Returns the output for a {@code {@systemProperty...}} tag.
     *
     * @param element the element that owns the doc comment
     * @param tag     the system property tag
     *
     * @return the output
     */
    private Content systemPropertyTagOutput(Element element, SystemPropertyTree tag) {
        String tagText = tag.getPropertyName().toString();
        return HtmlTree.CODE(tagletWriter.createAnchorAndSearchIndex(element, tagText,
                resources.getText("doclet.System_Property"), tag));
    }
}
