/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.org.apache.xerces.internal.xni;

import java.util.Enumeration;

/**
 * The Augmentations interface defines a table of additional data that may
 * be passed along the document pipeline. The information can contain extra
 * arguments or infoset augmentations, for example PSVI. This additional
 * information is identified by a String key.
 * <p>
 * <strong>Note:</strong>
 * Methods that receive Augmentations are required to copy the information
 * if it is to be saved for use beyond the scope of the method.
 * The Augmentations content is volatile, and maybe modified by any method in
 * any component in the pipeline. Therefore, methods passed this structure
 * should not save any reference to the structure.
 *
 * @author Elena Litani, IBM
 * @LastModified: Oct 2017
 */

public interface Augmentations {


    /**
     * Add additional information identified by a key to the Augmentations structure.
     *
     * @param key    Identifier, can't be <code>null</code>
     * @param item   Additional information
     *
     * @return the previous value of the specified key in the Augmentations structure,
     *         or <code>null</code> if it did not have one.
     */
    public Object putItem (String key, Object item);


    /**
     * Get information identified by a key from the Augmentations structure
     *
     * @param key    Identifier, can't be <code>null</code>
     *
     * @return the value to which the key is mapped in the Augmentations structure;
     *         <code>null</code> if the key is not mapped to any value.
     */
    public Object getItem(String key);


    /**
     * Remove additional info from the Augmentations structure
     *
     * @param key    Identifier, can't be <code>null</code>
     * @return the previous value of the specified key in the Augmentations structure,
     *         or <code>null</code> if it did not have one.
     */
    public Object removeItem (String key);


    /**
     * Returns an enumeration of the keys in the Augmentations structure
     *
     */
    public Enumeration<Object> keys ();


    /**
     * Remove all objects from the Augmentations structure.
     */
    public void removeAllItems ();

}
