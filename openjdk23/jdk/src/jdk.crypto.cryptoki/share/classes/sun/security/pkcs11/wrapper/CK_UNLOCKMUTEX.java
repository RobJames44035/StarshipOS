/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * interface CK_UNLOCKMUTEX<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_UNLOCKMUTEX {

    /**
     * Method CK_UNLOCKMUTEX
     *
     * @param pMutex The mutex (lock) object to unlock.
     * @exception PKCS11Exception
     */
    public void CK_UNLOCKMUTEX(Object pMutex) throws PKCS11Exception;

}
