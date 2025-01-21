/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package com.sun.org.apache.xml.internal.security.utils.resolver;

import java.util.Collections;
import java.util.Map;

import org.w3c.dom.Attr;

public class ResourceResolverContext {

    private final Map<String, String> properties;

    public final String uriToResolve;

    public final boolean secureValidation;

    public final String baseUri;

    public final Attr attr;

    public ResourceResolverContext(Attr attr, String baseUri, boolean secureValidation) {
        this(attr, baseUri, secureValidation, Collections.emptyMap());
    }

    public ResourceResolverContext(Attr attr, String baseUri, boolean secureValidation, Map<String, String> properties) {
        this.attr = attr;
        this.baseUri = baseUri;
        this.secureValidation = secureValidation;
        this.uriToResolve = attr != null ? attr.getValue() : null;
        this.properties = Collections.unmodifiableMap(properties != null ? properties : Collections.emptyMap());
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
