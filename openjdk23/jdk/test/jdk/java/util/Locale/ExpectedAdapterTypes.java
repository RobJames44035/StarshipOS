/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8008577 8138613 8174269
 * @summary Check whether CLDR locale provider adapter is enabled by default
 * @compile -XDignore.symbol.file ExpectedAdapterTypes.java
 * @modules java.base/sun.util.locale.provider
 * @run junit ExpectedAdapterTypes
 */

import java.util.Arrays;
import java.util.List;
import sun.util.locale.provider.LocaleProviderAdapter;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExpectedAdapterTypes {

    static final LocaleProviderAdapter.Type[] expected = {
        LocaleProviderAdapter.Type.CLDR,
        LocaleProviderAdapter.Type.FALLBACK,
    };

    /**
     * This test ensures LocaleProviderAdapter.getAdapterPreference() returns
     * the correct preferred adapter types. This test should fail whenever a change is made
     * to the implementation and the expected list is not updated accordingly.
     */
    @Test
    public void correctAdapterListTest() {
        List<LocaleProviderAdapter.Type> actualTypes = LocaleProviderAdapter.getAdapterPreference();
        List<LocaleProviderAdapter.Type> expectedTypes = Arrays.asList(expected);
        assertEquals(actualTypes, expectedTypes, String.format("getAdapterPreference() " +
                "returns: %s, but the expected adapter list returns: %s", actualTypes, expectedTypes));
    }
}
