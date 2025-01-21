/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils.resolver;

import com.sun.org.apache.xml.internal.security.exceptions.XMLSecurityException;

/**
 * This Exception is thrown if something related to the
 * {@link com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver} goes wrong.
 *
 */
public class ResourceResolverException extends XMLSecurityException {

    private static final long serialVersionUID = 1L;

    private String uri;

    private String baseURI;

    /**
     * Constructor ResourceResolverException
     *
     * @param msgID
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, String uri, String baseURI) {
        super(msgID);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     * Constructor ResourceResolverException
     *
     * @param msgID
     * @param exArgs
     * @param uri
     * @param baseURI
     */
    public ResourceResolverException(String msgID, Object[] exArgs, String uri,
                                     String baseURI) {
        super(msgID, exArgs);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    /**
     * Constructor ResourceResolverException
     *
     * @param originalException
     * @param uri
     * @param baseURI
     * @param msgID
     */
    public ResourceResolverException(Exception originalException,
                                     String uri, String baseURI, String msgID) {
        super(originalException, msgID);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    @Deprecated
    public ResourceResolverException(String msgID, Exception originalException,
                                     String uri, String baseURI) {
        this(originalException, uri, baseURI, msgID);
    }

    /**
     * Constructor ResourceResolverException
     *
     * @param originalException
     * @param uri
     * @param baseURI
     * @param msgID
     * @param exArgs
     */
    public ResourceResolverException(Exception originalException, String uri,
                                     String baseURI, String msgID, Object[] exArgs) {
        super(originalException, msgID, exArgs);

        this.uri = uri;
        this.baseURI = baseURI;
    }

    @Deprecated
    public ResourceResolverException(String msgID, Object[] exArgs,
                                     Exception originalException, String uri,
                                     String baseURI) {
        this(originalException, uri, baseURI, msgID, exArgs);
    }

    /**
     *
     * @param uri
     */
    public void setURI(String uri) {
        this.uri = uri;
    }

    /**
     *
     * @return the uri
     */
    public String getURI() {
        return this.uri;
    }

    /**
     *
     * @param baseURI
     */
    public void setbaseURI(String baseURI) {
        this.baseURI = baseURI;
    }

    /**
     *
     * @return the baseURI
     */
    public String getbaseURI() {
        return this.baseURI;
    }

}
