/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4052440 8000273 8062588 8210406 8174269
 * @summary LocaleNameProvider tests
 * @library providersrc/foobarutils
 *          providersrc/barprovider
 * @modules java.base/sun.util.locale.provider
 *          java.base/sun.util.resources
 * @build com.foobar.Utils
 *        com.bar.*
 * @run main/othervm -Djava.locale.providers=CLDR,SPI LocaleNameProviderTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

import com.bar.LocaleNameProviderImpl;

import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.OpenListResourceBundle;

public class LocaleNameProviderTest extends ProviderTest {

    public static void main(String[] s) {
        new LocaleNameProviderTest();
    }

    LocaleNameProviderTest() {
        checkAvailLocValidityTest();
        variantFallbackTest();
    }

    void checkAvailLocValidityTest() {
        LocaleNameProviderImpl lnp = new LocaleNameProviderImpl();
        Locale[] availloc = Locale.getAvailableLocales();
        Locale[] testloc = availloc.clone();
        List<Locale> jreimplloc = Arrays.asList(LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.CLDR).getLocaleNameProvider().getAvailableLocales());
        List<Locale> providerloc = Arrays.asList(lnp.getAvailableLocales());

        for (Locale target: availloc) {
            // pure JRE implementation
            OpenListResourceBundle rb = ((ResourceBundleBasedAdapter)LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.CLDR)).getLocaleData().getLocaleNames(target);
            boolean jreSupportsTarget = jreimplloc.contains(target);

            for (Locale test: testloc) {
                // codes
                String lang = test.getLanguage();
                String ctry = test.getCountry();
                String vrnt = test.getVariant();

                // the localized name
                String langresult = test.getDisplayLanguage(target);
                String ctryresult = test.getDisplayCountry(target);
                String vrntresult = test.getDisplayVariant(target);

                // provider's name (if any)
                String providerslang = null;
                String providersctry = null;
                String providersvrnt = null;
                if (providerloc.contains(target)) {
                    providerslang = lnp.getDisplayLanguage(lang, target);
                    providersctry = lnp.getDisplayCountry(ctry, target);
                    providersvrnt = lnp.getDisplayVariant(vrnt, target);
                }

                // JRE's name
                String jreslang = null;
                String jresctry = null;
                String jresvrnt = null;
                if (!lang.equals("")) {
                    try {
                        jreslang = rb.getString(lang);
                    } catch (MissingResourceException mre) {}
                }
                if (!ctry.equals("")) {
                    try {
                        jresctry = rb.getString(ctry);
                    } catch (MissingResourceException mre) {}
                }
                if (!vrnt.equals("")) {
                    try {
                        jresvrnt = rb.getString("%%"+vrnt);
                    } catch (MissingResourceException mre) {}
                }

                System.out.print("For key: "+lang+" ");
                checkValidity(target, jreslang, providerslang, langresult,
                    jreSupportsTarget && jreslang != null);
                System.out.print("For key: "+ctry+" ");
                checkValidity(target, jresctry, providersctry, ctryresult,
                    jreSupportsTarget && jresctry != null);
                System.out.print("For key: "+vrnt+" ");
                checkValidity(target, jresvrnt, providersvrnt, vrntresult,
                    jreSupportsTarget && jresvrnt != null);
            }
        }
    }

    void variantFallbackTest() {
        Locale YY = Locale.of("yy", "YY", "YYYY");
        Locale YY_suffix = Locale.of("yy", "YY", "YYYY_suffix");
        String retVrnt = null;
        String message = "variantFallbackTest() succeeded.";


        try {
            YY.getDisplayVariant(YY_suffix);
            message = "variantFallbackTest() failed. Either provider wasn't invoked, or invoked without suffix.";
        } catch (RuntimeException re) {
            retVrnt = re.getMessage();
            if (YY_suffix.getVariant().equals(retVrnt)) {
                System.out.println(message);
                return;
            }
            message = "variantFallbackTest() failed. Returned variant: "+retVrnt;
        }

        throw new RuntimeException(message);
    }
}
