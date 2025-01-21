/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * interface CK_NOTIFY.<p>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public interface CK_NOTIFY {

    /**
     * Method CK_NOTIFY
     *
     * @param hSession
     * @param event
     * @param pApplication
     * @exception PKCS11Exception
     */
    public void CK_NOTIFY(long hSession, long event, Object pApplication) throws PKCS11Exception;

}
