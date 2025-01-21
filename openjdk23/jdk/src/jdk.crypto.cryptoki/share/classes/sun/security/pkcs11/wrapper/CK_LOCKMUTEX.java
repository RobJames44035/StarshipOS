/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * interface CK_LOCKMUTEX<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_LOCKMUTEX {

    /**
     * Method CK_LOCKMUTEX
     *
     * @param pMutex The mutex (lock) object to lock.
     * @exception PKCS11Exception
     */
    public void CK_LOCKMUTEX(Object pMutex) throws PKCS11Exception;

}
