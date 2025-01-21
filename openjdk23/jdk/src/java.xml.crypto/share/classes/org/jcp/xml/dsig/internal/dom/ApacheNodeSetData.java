/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package org.jcp.xml.dsig.internal.dom;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.xml.crypto.NodeSetData;

import com.sun.org.apache.xml.internal.security.signature.NodeFilter;
import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.transforms.TransformationException;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import org.w3c.dom.Node;

public class ApacheNodeSetData implements ApacheData, NodeSetData<Node> {

    private XMLSignatureInput xi;

    public ApacheNodeSetData(XMLSignatureInput xi) {
        this.xi = xi;
    }

    @Override
    public Iterator<Node> iterator() {
        // If nodefilters are set, must execute them first to create node-set
        try {
            if (xi.getNodeFilters() != null && !xi.getNodeFilters().isEmpty()) {
                return Collections.unmodifiableSet
                        (getNodeSet(xi.getNodeFilters())).iterator();
            }
            return Collections.unmodifiableSet(xi.getNodeSet()).iterator();
        } catch (Exception e) {
            // should not occur
            throw new RuntimeException
                ("unrecoverable error retrieving nodeset", e);
        }
    }

    @Override
    public XMLSignatureInput getXMLSignatureInput() {
        return xi;
    }

    private Set<Node> getNodeSet(List<NodeFilter> nodeFilters)
            throws TransformationException {
        if (xi.isNeedsToBeExpanded()) {
            XMLUtils.circumventBug2650
                (XMLUtils.getOwnerDocument(xi.getSubNode()));
        }

        Set<Node> inputSet = new LinkedHashSet<>();
        XMLUtils.getSet(xi.getSubNode(), inputSet,
                        null, !xi.isExcludeComments());
        Set<Node> nodeSet = new LinkedHashSet<>();
        for (Node currentNode : inputSet) {
            Iterator<NodeFilter> it = nodeFilters.iterator();
            boolean skipNode = false;
            while (it.hasNext() && !skipNode) {
                NodeFilter nf = it.next();
                if (nf.isNodeInclude(currentNode) != 1) {
                    skipNode = true;
                }
            }
            if (!skipNode) {
                nodeSet.add(currentNode);
            }
        }
        return nodeSet;
    }
}
