/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.time.chrono;

import static java.time.temporal.ChronoField.ERA;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * An era in the Minguo calendar system.
 * <p>
 * The Minguo calendar system has two eras.
 * The current era, for years from 1 onwards, is known as the 'Republic of China' era.
 * All previous years, zero or earlier in the proleptic count or one and greater
 * in the year-of-era count, are part of the 'Before Republic of China' era.
 *
 * <table class="striped" style="text-align:left">
 * <caption style="display:none">Minguo years and eras</caption>
 * <thead>
 * <tr>
 * <th>year-of-era</th>
 * <th>era</th>
 * <th>proleptic-year</th>
 * <th>ISO proleptic-year</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>2</td><td>ROC</td><th scope="row">2</th><td>1913</td>
 * </tr>
 * <tr>
 * <td>1</td><td>ROC</td><th scope="row">1</th><td>1912</td>
 * </tr>
 * <tr>
 * <td>1</td><td>BEFORE_ROC</td><th scope="row">0</th><td>1911</td>
 * </tr>
 * <tr>
 * <td>2</td><td>BEFORE_ROC</td><th scope="row">-1</th><td>1910</td>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code MinguoEra}.
 * Use {@code getValue()} instead.</b>
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * @since 1.8
 */
public enum MinguoEra implements Era {

    /**
     * The singleton instance for the era before the current one, 'Before Republic of China Era',
     * which has the numeric value 0.
     */
    BEFORE_ROC,
    /**
     * The singleton instance for the current era, 'Republic of China Era',
     * which has the numeric value 1.
     */
    ROC;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code MinguoEra} from an {@code int} value.
     * <p>
     * {@code MinguoEra} is an enum representing the Minguo eras of BEFORE_ROC/ROC.
     * This factory allows the enum to be obtained from the {@code int} value.
     *
     * @param minguoEra  the BEFORE_ROC/ROC value to represent, from 0 (BEFORE_ROC) to 1 (ROC)
     * @return the era singleton, not null
     * @throws DateTimeException if the value is invalid
     */
    public static MinguoEra of(int minguoEra) {
        return switch (minguoEra) {
            case 0  -> BEFORE_ROC;
            case 1  -> ROC;
            default -> throw new DateTimeException("Invalid era: " + minguoEra);
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the numeric era {@code int} value.
     * <p>
     * The era BEFORE_ROC has the value 0, while the era ROC has the value 1.
     *
     * @return the era value, from 0 (BEFORE_ROC) to 1 (ROC)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

    /**
     * {@inheritDoc}
     *
     * @param style {@inheritDoc}
     * @param locale {@inheritDoc}
     */
    @Override
    public String getDisplayName(TextStyle style, Locale locale) {
        return new DateTimeFormatterBuilder()
            .appendText(ERA, style)
            .toFormatter(locale)
            .withChronology(MinguoChronology.INSTANCE)
            .format(this == ROC ? MinguoDate.of(1, 1, 1) : MinguoDate.of(0, 1, 1));
    }

}
