/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.util.locale.provider;

import java.text.Collator;
import java.text.ParseException;
import java.text.RuleBasedCollator;
import java.text.spi.CollatorProvider;
import java.util.Locale;
import java.util.Set;

/**
 * Concrete implementation of the
 * {@link java.text.spi.CollatorProvider CollatorProvider} class
 * for the JRE LocaleProviderAdapter.
 */
public class CollatorProviderImpl extends CollatorProvider implements AvailableLanguageTags {
    private final LocaleProviderAdapter.Type type;
    private final Set<String> langtags;

    public CollatorProviderImpl(LocaleProviderAdapter.Type type, Set<String> langtags) {
        this.type = type;
        this.langtags = langtags;
    }

    /**
     * Returns an array of all locales for which this locale service provider
     * can provide localized objects or names.
     *
     * @return An array of all locales for which this locale service provider
     * can provide localized objects or names.
     */
    @Override
    public Locale[] getAvailableLocales() {
        return LocaleProviderAdapter.toLocaleArray(langtags);
    }

    @Override
    public boolean isSupportedLocale(Locale locale) {
        return LocaleProviderAdapter.forType(type).isSupportedProviderLocale(locale, langtags);
    }

    /**
     * Returns a new <code>Collator</code> instance for the specified locale.
     * @param locale the desired locale.
     * @return the <code>Collator</code> for the desired locale.
     * @exception NullPointerException if
     * <code>locale</code> is null
     * @exception IllegalArgumentException if <code>locale</code> isn't
     *     one of the locales returned from
     *     {@link java.util.spi.LocaleServiceProvider#getAvailableLocales()
     *     getAvailableLocales()}.
     * @see java.text.Collator#getInstance(java.util.Locale)
     */
    @Override
    public Collator getInstance(Locale locale) {
        if (locale == null) {
            throw new NullPointerException();
        }

        Collator result = null;

        // Load the resource of the desired locale from resource
        // manager.
        String colString = LocaleProviderAdapter.forType(type).getLocaleResources(locale).getCollationData();
        try
        {
            result = new RuleBasedCollator(CollationRules.DEFAULTRULES +
                                           colString);
        }
        catch(ParseException foo)
        {
            // predefined tables should contain correct grammar
            try {
                result = new RuleBasedCollator(CollationRules.DEFAULTRULES);
            } catch (ParseException bar) {
                // the default rules should always be parsable.
                throw new InternalError(bar);
            }
        }
        // Now that RuleBasedCollator adds expansions for pre-composed characters
        // into their decomposed equivalents, the default collators don't need
        // to have decomposition turned on.  Laura, 5/5/98, bug 4114077
        result.setDecomposition(Collator.NO_DECOMPOSITION);

        return (Collator)result.clone();
    }

    @Override
    public Set<String> getAvailableLanguageTags() {
        return langtags;
    }
}
