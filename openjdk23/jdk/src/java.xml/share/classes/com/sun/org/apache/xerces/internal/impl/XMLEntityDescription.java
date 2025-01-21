/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.impl;

import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

/**
 * <p>This interface describes the properties of entities--their
 * physical location and their name.</p>
 *
 * @xerces.internal
 *
 * @author Michael Glavassevich, IBM
 *
 */
public interface XMLEntityDescription extends XMLResourceIdentifier {

    /**
     * Sets the name of the entity.
     *
     * @param name the name of the entity
     */
    public void setEntityName(String name);

    /**
     * Returns the name of the entity.
     *
     * @return the name of the entity
     */
    public String getEntityName();

} // XMLEntityDescription
