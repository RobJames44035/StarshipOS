/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import static catalog.CatalogTestUtils.catalogResolver;
import static catalog.ResolutionChecker.checkExtIdResolution;

import javax.xml.catalog.CatalogResolver;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.PreferTest
 * @summary Get matched URIs from system and public family entries, which
 *          specify the prefer attribute. It tests how does the prefer attribute
 *          affect the resolution procedure. The test rule is based on OASIS
 *          Standard V1.1 section 4.1.1. "The prefer attribute".
 */
public class PreferTest {

    @Test(dataProvider = "publicId-systemId-matchedUri")
    public void testPrefer(String publicId, String systemId,
            String expected) {
        checkExtIdResolution(createResolver(), publicId, systemId, expected);
    }

    @DataProvider(name = "publicId-systemId-matchedUri")
    public Object[][] data() {
        return new Object[][] {
                // The prefer attribute is public. Both of the specified public
                // id and system id have matches in the catalog file. But
                // finally, the returned URI is the system match.
                { "-//REMOTE//DTD ALICE DOCALICE XML//EN",
                        "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/base/dtd/docAliceSys.dtd" },

                // The prefer attribute is public, and only the specified system
                // id has match. The returned URI is the system match.
                { "-//REMOTE//DTD ALICE DOCALICEDUMMY XML//EN",
                        "http://remote/dtd/alice/docAlice.dtd",
                        "http://local/base/dtd/docAliceSys.dtd"},

                // The prefer attribute is public, and only the specified public
                // id has match. The returned URI is the system match.
                { "-//REMOTE//DTD ALICE DOCALICE XML//EN",
                        "http://remote/dtd/alice/docAliceDummy.dtd",
                        "http://local/base/dtd/docAlicePub.dtd" },

                // The prefer attribute is system, and both of the specified
                // system id and public id have matches. But the returned URI is
                // the system match.
                { "-//REMOTE//DTD BOB DOCBOB XML//EN",
                        "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/docBobSys.dtd" },

                // The prefer attribute is system, and only system id has match.
                // The returned URI is the system match.
                { "-//REMOTE//DTD BOB DOCBOBDUMMY XML//EN",
                        "http://remote/dtd/bob/docBob.dtd",
                        "http://local/base/dtd/docBobSys.dtd" } };
    }

    private CatalogResolver createResolver() {
        return catalogResolver("prefer.xml");
    }
}
