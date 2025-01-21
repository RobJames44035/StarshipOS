/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jaxp.library;

import org.testng.annotations.DataProvider;

/**
 * Provide invalid parameters for negative testing Factory.newInstance.
 */
public class JAXPDataProvider {

    @DataProvider(name = "new-instance-neg")
    public static Object[][] getNewInstanceNeg() {
        return new Object[][] { { null, null }, { null, JAXPDataProvider.class.getClassLoader() } };
    }

}
