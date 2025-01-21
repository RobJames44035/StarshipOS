/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.parser;

import com.sun.org.apache.xerces.internal.xni.XMLDTDHandler;

/**
 * Defines a DTD filter that acts as both a receiver and an emitter
 * of DTD events.
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLDTDFilter
    extends XMLDTDHandler, XMLDTDSource {

} // interface XMLDTDFilter
