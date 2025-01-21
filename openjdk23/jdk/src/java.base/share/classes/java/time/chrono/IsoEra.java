/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.time.chrono;

import java.time.DateTimeException;

/**
 * An era in the ISO calendar system.
 * <p>
 * The ISO-8601 standard does not define eras.
 * A definition has therefore been created with two eras - 'Current era' (CE) for
 * years on or after 0001-01-01 (ISO), and 'Before current era' (BCE) for years before that.
 *
 * <table class="striped" style="text-align:left">
 * <caption style="display:none">ISO years and eras</caption>
 * <thead>
 * <tr>
 * <th scope="col">year-of-era</th>
 * <th scope="col">era</th>
 * <th scope="col">proleptic-year</th>
 * </tr>
 * </thead>
 * <tbody>
 * <tr>
 * <td>2</td><td>CE</td><th scope="row">2</th>
 * </tr>
 * <tr>
 * <td>1</td><td>CE</td><th scope="row">1</th>
 * </tr>
 * <tr>
 * <td>1</td><td>BCE</td><th scope="row">0</th>
 * </tr>
 * <tr>
 * <td>2</td><td>BCE</td><th scope="row">-1</th>
 * </tr>
 * </tbody>
 * </table>
 * <p>
 * <b>Do not use {@code ordinal()} to obtain the numeric representation of {@code IsoEra}.
 * Use {@code getValue()} instead.</b>
 *
 * @implSpec
 * This is an immutable and thread-safe enum.
 *
 * @since 1.8
 */
public enum IsoEra implements Era {

    /**
     * The singleton instance for the era before the current one, 'Before Current Era',
     * which has the numeric value 0.
     */
    BCE,
    /**
     * The singleton instance for the current era, 'Current Era',
     * which has the numeric value 1.
     */
    CE;

    //-----------------------------------------------------------------------
    /**
     * Obtains an instance of {@code IsoEra} from an {@code int} value.
     * <p>
     * {@code IsoEra} is an enum representing the ISO eras of BCE/CE.
     * This factory allows the enum to be obtained from the {@code int} value.
     *
     * @param isoEra  the BCE/CE value to represent, from 0 (BCE) to 1 (CE)
     * @return the era singleton, not null
     * @throws DateTimeException if the value is invalid
     */
    public static IsoEra of(int isoEra) {
        return switch (isoEra) {
            case 0  -> BCE;
            case 1  -> CE;
            default -> throw new DateTimeException("Invalid era: " + isoEra);
        };
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the numeric era {@code int} value.
     * <p>
     * The era BCE has the value 0, while the era CE has the value 1.
     *
     * @return the era value, from 0 (BCE) to 1 (CE)
     */
    @Override
    public int getValue() {
        return ordinal();
    }

}
