/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xs;

/**
 * This interface represents the Model Group schema component.
 */
public interface XSModelGroup extends XSTerm {
    // Content model compositors
    /**
     * This constant value signifies a sequence operator.
     */
    public static final short COMPOSITOR_SEQUENCE       = 1;
    /**
     * This constant value signifies a choice operator.
     */
    public static final short COMPOSITOR_CHOICE         = 2;
    /**
     * This content model represents a simplified version of the SGML
     * &amp;-Connector and is limited to the top-level of any content model.
     * No element in the all content model may appear more than once.
     */
    public static final short COMPOSITOR_ALL            = 3;

    /**
     * [compositor]: one of all, choice or sequence. The valid constant values
     * are:
     * <code>COMPOSITOR_SEQUENCE, COMPOSITOR_CHOICE, COMPOSITOR_ALL</code>.
     */
    public short getCompositor();

    /**
     *  A list of [particles] if it exists, otherwise an empty
     * <code>XSObjectList</code>.
     */
    public XSObjectList getParticles();

    /**
     * An annotation if it exists, otherwise <code>null</code>. If not null
     * then the first [annotation] from the sequence of annotations.
     */
    public XSAnnotation getAnnotation();

    /**
     * A sequence of [annotations] or an empty <code>XSObjectList</code>.
     */
    public XSObjectList getAnnotations();
}
