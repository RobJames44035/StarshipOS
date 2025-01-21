/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package javax.xml.catalog;

/**
 * Represents a nextCatalog entry.
 *
 * @since 9
 */
final class NextCatalog extends AltCatalog {

    /**
     * Construct a nextCatalog entry.
     * @param catalog The catalog attribute.
     */
    public NextCatalog(String base, String catalog) {
        super(CatalogEntryType.NEXTCATALOG, base);
        setCatalog(catalog);
    }

    @Override
    public String match(String match) {
        throw new UnsupportedOperationException("Not supported.");
    }

}
