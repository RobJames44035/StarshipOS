/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.xni.parser;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * This interface is used to resolve external parsed entities. The
 * application can register an object that implements this interface
 * with the parser configuration in order to intercept entities and
 * resolve them explicitly. If the registered entity resolver cannot
 * resolve the entity, it should return <code>null</code> so that the
 * parser will try to resolve the entity using a default mechanism.
 *
 * @see XMLParserConfiguration
 *
 * @author Andy Clark, IBM
 *
 */
public interface XMLEntityResolver {

    //
    // XMLEntityResolver methods
    //

    /**
     * Resolves an external parsed entity. If the entity cannot be
     * resolved, this method should return null.
     *
     * @param resourceIdentifier location of the XML resource to resolve
     *
     * @throws XNIException Thrown on general error.
     * @throws IOException  Thrown if resolved entity stream cannot be
     *                      opened or some other i/o error occurs.
     * @see com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier
     */
    public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier)
        throws XNIException, IOException;

} // interface XMLEntityResolver
