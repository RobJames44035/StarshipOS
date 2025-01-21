/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package catalog;

import javax.xml.catalog.CatalogFeatures;
import javax.xml.catalog.CatalogFeatures.Feature;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
 * @test
 * @bug 8077931
 * @library /javax/xml/jaxp/libs
 * @run testng/othervm catalog.DefaultFeaturesTest
 * @summary This case tests if the default feature values are expected.
 */
public class DefaultFeaturesTest {

    private CatalogFeatures defaultFeature;

    @BeforeClass
    public void init() {
        defaultFeature = CatalogFeatures.defaults();
    }

    @Test(dataProvider="feature-value")
    public void testDefaultFeatures(Feature feature, String expected) {
        String featureValue = defaultFeature.get(feature);
        if (expected != null) {
            Assert.assertEquals(featureValue, expected);
        } else {
            Assert.assertNull(featureValue);
        }
    }

    @DataProvider(name = "feature-value")
    public Object[][] data() {
        return new Object[][] {
                { Feature.FILES, null },
                { Feature.PREFER, CatalogTestUtils.PREFER_PUBLIC },
                { Feature.DEFER, CatalogTestUtils.DEFER_TRUE },
                { Feature.RESOLVE, CatalogTestUtils.RESOLVE_STRICT } };
    }
}
