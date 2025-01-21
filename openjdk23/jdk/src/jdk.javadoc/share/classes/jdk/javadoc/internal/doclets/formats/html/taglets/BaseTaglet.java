/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package jdk.javadoc.internal.doclets.formats.html.taglets;

import java.util.Set;

import javax.lang.model.element.Element;

import com.sun.source.doctree.DocTree;

import jdk.javadoc.doclet.Taglet.Location;
import jdk.javadoc.internal.doclets.formats.html.HtmlConfiguration;
import jdk.javadoc.internal.doclets.toolkit.Messages;
import jdk.javadoc.internal.doclets.toolkit.Resources;
import jdk.javadoc.internal.doclets.toolkit.util.Utils;
import jdk.javadoc.internal.html.Content;

/**
 * A base class that implements the {@link Taglet} interface.
 */
public class BaseTaglet implements Taglet {
    // The following members are global to the lifetime of the doclet
    protected final HtmlConfiguration config;
    protected final Messages messages;
    protected final Resources resources;
    protected final Utils utils;

    // The following members are specific to the instance of the taglet
    protected final DocTree.Kind tagKind;
    protected final String name;
    private final boolean inline;
    private final Set<Location> sites;

    // The following is dynamically set for the duration of the methods
    //      getInlineTagOutput and getAllBlockTagOutput
    // by those taglets that need to refer to it
    protected TagletWriter tagletWriter;

    public BaseTaglet(HtmlConfiguration config, DocTree.Kind tagKind, boolean inline, Set<Location> sites) {
        this(config, tagKind.tagName, tagKind, inline, sites);
    }

    protected BaseTaglet(HtmlConfiguration config, String name, boolean inline, Set<Location> sites) {
        this(config, name, inline ? DocTree.Kind.UNKNOWN_INLINE_TAG : DocTree.Kind.UNKNOWN_BLOCK_TAG, inline, sites);
    }

    private BaseTaglet(HtmlConfiguration config, String name, DocTree.Kind tagKind, boolean inline, Set<Location> sites) {
        this.config = config;
        this.messages = config.getMessages();
        this.resources = config.getDocResources();
        this.utils = config.utils;

        this.name = name;
        this.tagKind = tagKind;
        this.inline = inline;
        this.sites = sites;
    }

    @Override
    public Set<Location> getAllowedLocations() {
        return sites;
    }

    @Override
    public final boolean isInlineTag() {
        return inline;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the kind of trees recognized by this taglet.
     *
     * @return the kind of trees recognized by this taglet
     */
    public DocTree.Kind getTagKind() {
        return tagKind;
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec This implementation throws {@link UnsupportedTagletOperationException}.
     */
    @Override
    public Content getInlineTagOutput(Element element, DocTree tag, TagletWriter tagletWriter) {
        throw new UnsupportedTagletOperationException("Method not supported in taglet " + getName() + ".");
    }

    /**
     * {@inheritDoc}
     *
     * @implSpec This implementation throws {@link UnsupportedTagletOperationException}
     */
    @Override
    public Content getAllBlockTagOutput(Element holder, TagletWriter tagletWriter) {
        throw new UnsupportedTagletOperationException("Method not supported in taglet " + getName() + ".");
    }
}
