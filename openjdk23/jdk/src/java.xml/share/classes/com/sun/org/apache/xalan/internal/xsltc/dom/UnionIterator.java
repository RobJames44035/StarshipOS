/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc.dom;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIteratorBase;

/**
 * UnionIterator takes a set of NodeIterators and produces
 * a merged NodeSet in document order with duplicates removed
 * The individual iterators are supposed to generate nodes
 * in document order
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public final class UnionIterator extends MultiValuedNodeHeapIterator {
    /** wrapper for NodeIterators to support iterator
        comparison on the value of their next() method
    */
    final private DOM _dom;

    private final class LookAheadIterator
            extends MultiValuedNodeHeapIterator.HeapNode
    {
        public DTMAxisIterator iterator;

        public LookAheadIterator(DTMAxisIterator iterator) {
            super();
            this.iterator = iterator;
        }

        public int step() {
            _node = iterator.next();
            return _node;
        }

        public HeapNode cloneHeapNode() {
            LookAheadIterator clone = (LookAheadIterator) super.cloneHeapNode();
            clone.iterator = iterator.cloneIterator();
            return clone;
        }

        public void setMark() {
            super.setMark();
            iterator.setMark();
        }

        public void gotoMark() {
            super.gotoMark();
            iterator.gotoMark();
        }

        public boolean isLessThan(HeapNode heapNode) {
            LookAheadIterator comparand = (LookAheadIterator) heapNode;
            return _dom.lessThan(_node, heapNode._node);
        }

        public HeapNode setStartNode(int node) {
            iterator.setStartNode(node);
            return this;
        }

        public HeapNode reset() {
            iterator.reset();
            return this;
        }
    } // end of LookAheadIterator

    public UnionIterator(DOM dom) {
        _dom = dom;
    }

    public UnionIterator addIterator(DTMAxisIterator iterator) {
        addHeapNode(new LookAheadIterator(iterator));
        return this;
    }
}
