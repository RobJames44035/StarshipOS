/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils.resolver.implementations;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.org.apache.xml.internal.security.signature.XMLSignatureInput;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverContext;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverException;
import com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolverSpi;

/**
 * A simple ResourceResolver for requests into the local filesystem.
 */
public class ResolverLocalFilesystem extends ResourceResolverSpi {

    private static final com.sun.org.slf4j.internal.Logger LOG =
        com.sun.org.slf4j.internal.LoggerFactory.getLogger(ResolverLocalFilesystem.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public XMLSignatureInput engineResolveURI(ResourceResolverContext context)
        throws ResourceResolverException {
        try {
            // calculate new URI
            URI uriNew = getNewURI(context.uriToResolve, context.baseUri);

            InputStream inputStream = Files.newInputStream(Paths.get(uriNew));  //NOPMD
            XMLSignatureInput result = new XMLSignatureInput(inputStream);
            result.setSecureValidation(context.secureValidation);

            result.setSourceURI(uriNew.toString());

            return result;
        } catch (Exception e) {
            throw new ResourceResolverException(e, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean engineCanResolveURI(ResourceResolverContext context) {
        if (context.uriToResolve == null) {
            return false;
        }

        if (context.uriToResolve.isEmpty() || context.uriToResolve.charAt(0) == '#' ||
            context.uriToResolve.startsWith("http:") || context.uriToResolve.startsWith("https:")) {
            return false;
        }

        try {
            LOG.debug("I was asked whether I can resolve {}", context.uriToResolve);

            if (context.uriToResolve.startsWith("file:") || context.baseUri.startsWith("file:")) {
                LOG.debug("I state that I can resolve {}", context.uriToResolve);
                return true;
            }
        } catch (Exception e) {
            LOG.debug(e.getMessage(), e);
        }

        LOG.debug("But I can't");

        return false;
    }

    private static URI getNewURI(String uri, String baseURI) throws URISyntaxException {
        URI newUri = null;
        if (baseURI == null || baseURI.length() == 0) {
            newUri = new URI(uri);
        } else {
            newUri = new URI(baseURI).resolve(uri);
        }

        // if the URI contains a fragment, ignore it
        if (newUri.getFragment() != null) {
            return new URI(newUri.getScheme(), newUri.getSchemeSpecificPart(), null);
        }
        return newUri;
    }
}
