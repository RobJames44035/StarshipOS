/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package javax.xml.catalog;

/**
 * Represents the catalog element or entry of a catalog file
 *
 * @since 9
 */
class CatalogEntry extends GroupEntry {

    /**
     * Construct a catalog entry.
     *
     * @param base The baseURI attribute
     * @param attributes The attributes
     */
    public CatalogEntry(String base, String... attributes) {
        super(base, attributes);
        setType(CatalogEntryType.CATALOGENTRY);
    }

    @Override
    public String match(String match) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

}
