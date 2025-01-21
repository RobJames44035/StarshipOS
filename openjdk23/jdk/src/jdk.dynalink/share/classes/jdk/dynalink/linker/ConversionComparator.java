/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * This file is available under and governed by the GNU General Public
 * License version 2 only, as published by the Free Software Foundation.
 * However, the following notice accompanied the original version of this
 * file, and Oracle licenses the original version of this file under the BSD
 * license:
 */
package jdk.dynalink.linker;


/**
 * Optional interface to be implemented by {@link GuardingTypeConverterFactory}
 * implementers. Language-specific conversions can cause increased overloaded
 * method resolution ambiguity, as many methods can become applicable because of
 * additional conversions. The static way of selecting the "most specific"
 * method will fail more often, because there will be multiple maximally
 * specific method with unrelated signatures. In these cases, language runtimes
 * can be asked to resolve the ambiguity by expressing preferences for one
 * conversion over the other.
 * @since 9
 */
public interface ConversionComparator {
    /**
     * Enumeration of possible outcomes of comparing one conversion to another.
     * @since 9
     */
    enum Comparison {
        /** The conversions cannot be compared. **/
        INDETERMINATE,
        /** The first conversion is better than the second one. **/
        TYPE_1_BETTER,
        /** The second conversion is better than the first one. **/
        TYPE_2_BETTER,
    }

    /**
     * Determines which of the two target types is the preferred conversion
     * target from a source type.
     * @param sourceType the source type.
     * @param targetType1 one potential target type
     * @param targetType2 another potential target type.
     * @return one of Comparison constants that establish which - if any - of
     * the target types is preferred for the conversion.
     */
    public Comparison compareConversion(Class<?> sourceType, Class<?> targetType1, Class<?> targetType2);
}
