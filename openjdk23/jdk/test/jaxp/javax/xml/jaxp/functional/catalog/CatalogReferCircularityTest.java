/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.catalogResolver;

import javax.xml.catalog.CatalogException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.CatalogReferCircularityTest
 * @summary Via nextCatalog entry, the catalog reference chain may be
 *          a (partial) closed circuit. For instance, a catalog may use itself
 *          as an additional catalog specified in its own nextCatalog entry.
 *          This case tests if the implementation handles this issue.
 */
public class CatalogReferCircularityTest {

    @Test(dataProvider = "catalogName",
            expectedExceptions = CatalogException.class)
    public void testReferCircularity(String catalogFile) {
        catalogResolver(catalogFile).resolveEntity(null,
                "http://remote/dtd/ghost/docGhost.dtd");
    }

    @DataProvider(name = "catalogName")
    public Object[][] catalogName() {
        return new Object[][] {
                // This catalog defines itself as next catalog.
                { "catalogReferCircle-itself.xml" },

                // This catalog defines catalogReferCircle-right.xml as its next
                // catalog. And catalogReferCircle-right.xml also defines
                // catalogReferCircle-left.xml as its next catalog, too.
                { "catalogReferCircle-left.xml" } };
    }
}
