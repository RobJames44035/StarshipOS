/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4052440 8062588 8210406 8174269
 * @summary CollatorProvider tests
 * @library providersrc/foobarutils
 *          providersrc/fooprovider
 * @modules java.base/sun.util.locale.provider
 *          java.base/sun.util.resources
 * @build com.foobar.Utils
 *        com.foo.*
 * @run main/othervm -Djava.locale.providers=CLDR,SPI CollatorProviderTest
 */

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;

import com.foo.CollatorProviderImpl;

import sun.util.locale.provider.AvailableLanguageTags;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;

public class CollatorProviderTest extends ProviderTest {

    CollatorProviderImpl cp = new CollatorProviderImpl();
    List<Locale> availloc = Arrays.asList(Collator.getAvailableLocales());
    List<Locale> providerloc = Arrays.asList(cp.getAvailableLocales());
    List<Locale> fallbackloc = Arrays.asList(LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.FALLBACK).getAvailableLocales());
    List<Locale> fallbackimplloc = Arrays.asList(LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.FALLBACK).getCollatorProvider().getAvailableLocales());

    public static void main(String[] s) {
        new CollatorProviderTest();
    }

    CollatorProviderTest() {
        availableLocalesTest();
        objectValidityTest();
    }

    void availableLocalesTest() {
        Set<Locale> localesFromAPI = new HashSet<>(availloc);
        Set<Locale> localesExpected = new HashSet<>(fallbackloc);
        localesExpected.addAll(providerloc);
        if (localesFromAPI.equals(localesExpected)) {
            System.out.println("availableLocalesTest passed.");
        } else {
            localesFromAPI.removeAll(localesExpected);
            throw new RuntimeException("availableLocalesTest failed"+ localesFromAPI);
        }
    }

    void objectValidityTest() {
        Collator def = Collator.getInstance(Locale.of(""));
        String defrules = ((RuleBasedCollator)def).getRules();

        for (Locale target: availloc) {
            // pure JRE implementation
            Set<Locale> fbil = new HashSet<>();
            for (String tag : ((AvailableLanguageTags)LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.FALLBACK).getCollatorProvider()).getAvailableLanguageTags()) {
                fbil.add(Locale.forLanguageTag(tag));
            }
            ResourceBundle rb = ((ResourceBundleBasedAdapter)LocaleProviderAdapter.forType(LocaleProviderAdapter.Type.FALLBACK)).getLocaleData().getCollationData(target);
            boolean fbSupportsLocale = fbil.contains(target);

            // result object
            Collator result = Collator.getInstance(target);

            // provider's object (if any)
            Collator providersResult = null;
            if (providerloc.contains(target)) {
                providersResult = cp.getInstance(target);
            }

            // Fallback rule
            Collator fbResult = null;
            if (fbSupportsLocale) {
                try {
                    String rules = rb.getString("Rule");
                    fbResult = new RuleBasedCollator(defrules+rules);
                    fbResult.setDecomposition(Collator.NO_DECOMPOSITION);
                } catch (MissingResourceException mre) {
                } catch (ParseException pe) {
                }
            }

            checkValidity(target, fbResult, providersResult, result, fbSupportsLocale);
        }
    }
}
