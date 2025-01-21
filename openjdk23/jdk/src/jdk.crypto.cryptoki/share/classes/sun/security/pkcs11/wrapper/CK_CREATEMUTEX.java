/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * interface CK_CREATEMUTEX.
 *
 * @author Karl Scheibelhofer &lt;Karl.Scheibelhofer@iaik.at&gt;
 * @author Martin Schlaeffer &lt;schlaeff@sbox.tugraz.at&gt;
 */
public interface CK_CREATEMUTEX {

    /**
     * Method CK_CREATEMUTEX
     *
     * @return The mutex (lock) object.
     * @exception PKCS11Exception
     */
    public Object CK_CREATEMUTEX() throws PKCS11Exception;

}
