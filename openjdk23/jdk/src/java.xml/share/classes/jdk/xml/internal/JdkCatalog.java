/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
package jdk.xml.internal;

import java.net.URI;
import javax.xml.catalog.Catalog;
import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogManager;

/**
 * Represents the built-in Catalog that hosts the DTDs for the Java platform.
 */
public class JdkCatalog {
    public static final String JDKCATALOG = "/jdk/xml/internal/jdkcatalog/JDKCatalog.xml";
    private static final String JDKCATALOG_URL = SecuritySupport.getResource(JDKCATALOG).toExternalForm();
    public static Catalog catalog;

    public static void init(String resolve) {
        if (catalog == null) {
            CatalogFeatures cf = JdkXmlUtils.getCatalogFeatures(null, JDKCATALOG_URL, null, resolve);
            catalog = CatalogManager.catalog(cf, URI.create(JDKCATALOG_URL));
        }
    }
}
