/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8004240
 * @summary Verify that getAdapterPreference returns an unmodifiable list.
 * @modules java.base/sun.util.locale.provider
 * @compile -XDignore.symbol.file GetAdapterPreference.java
 * @run junit GetAdapterPreference
 */

import java.util.List;
import sun.util.locale.provider.LocaleProviderAdapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GetAdapterPreference {

    /**
     * Test that the list returned from getAdapterPreference()
     * cannot be modified.
     */
    @Test
    public void immutableTest() {
        List<LocaleProviderAdapter.Type> types = LocaleProviderAdapter.getAdapterPreference();
        assertThrows(UnsupportedOperationException.class, () -> types.set(0, null),
                "Trying to modify list returned from LocaleProviderAdapter.getAdapterPreference() did not throw UOE");
    }
}
