/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xerces.internal.util;

/**
 * <p>A structure that represents an error code, characterized by
 * a domain and a message key.</p>
 *
 * @author Naela Nissar, IBM
 *
 */
final class XMLErrorCode {

    //
    // Data
    //

    /** error domain **/
    private String fDomain;

    /** message key **/
    private String fKey;

    /**
     * <p>Constructs an XMLErrorCode with the given domain and key.</p>
     *
     * @param domain The error domain.
     * @param key The key of the error message.
     */
    public XMLErrorCode(String domain, String key) {
        fDomain = domain;
        fKey = key;
    }

    /**
     * <p>Convenience method to set the values of an XMLErrorCode.</p>
     *
     * @param domain The error domain.
     * @param key The key of the error message.
     */
    public void setValues(String domain, String key) {
        fDomain = domain;
        fKey = key;
    }

    /**
     * <p>Indicates whether some other object is equal to this XMLErrorCode.</p>
     *
     * @param obj the object with which to compare.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof XMLErrorCode))
            return false;
        XMLErrorCode err = (XMLErrorCode) obj;
        return (fDomain.equals(err.fDomain) && fKey.equals(err.fKey));
    }

    /**
     * <p>Returns a hash code value for this XMLErrorCode.</p>
     *
     * @return a hash code value for this XMLErrorCode.
     */
    public int hashCode() {
        return fDomain.hashCode() + fKey.hashCode();
    }
}
