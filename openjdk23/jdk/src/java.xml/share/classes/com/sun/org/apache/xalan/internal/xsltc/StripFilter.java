/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xalan.internal.xsltc;


/**
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public interface StripFilter {
    public boolean stripSpace(DOM dom, int node, int type);
}
