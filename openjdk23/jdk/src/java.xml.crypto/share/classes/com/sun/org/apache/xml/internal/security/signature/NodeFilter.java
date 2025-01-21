/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.signature;

import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import org.w3c.dom.Node;

/**
 * An interface to tell to the c14n if a node is included or not in the output
 */
public interface NodeFilter {

    /**
     * Tells if a node must be output in c14n.
     * @param n
     * @return 1 if the node should be output.
     *            0 if node must not be output,
     *           -1 if the node and all it's child must not be output.
     *
     */
    int isNodeInclude(Node n) throws TransformationException;

    /**
     * Tells if a node must be output in a c14n.
     * The caller must assured that this method is always call
     * in document order. The implementations can use this
     * restriction to optimize the transformation.
     * @param n
     * @param level the relative level in the tree
     * @return 1 if the node should be output.
     *            0 if node must not be output,
     *           -1 if the node and all it's child must not be output.
     */
    int isNodeIncludeDO(Node n, int level) throws TransformationException;

}
