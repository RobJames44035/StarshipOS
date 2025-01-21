/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/**
 * <p>
 * Generic API for calendar systems other than the default ISO.
 * </p>
 * <p>
 * The main API is based around the calendar system defined in ISO-8601.
 * However, there are other calendar systems, and this package provides basic support for them.
 * The alternate calendars are provided in the {@link java.time.chrono} package.
 * </p>
 * <p>
 * A calendar system is defined by the {@link java.time.chrono.Chronology} interface,
 * while a date in a calendar system is defined by the {@link java.time.chrono.ChronoLocalDate} interface.
 * </p>
 * <p>
 * It is intended that applications use the main API whenever possible, including code to read and write
 * from a persistent data store, such as a database, and to send dates and times across a network.
 * The "chrono" classes are then used at the user interface level to deal with localized input/output.
 * See {@link java.time.chrono.ChronoLocalDate ChronoLocalDate}
 * for a full discussion of the issues.
 * </p>
 * <p>
 * Using non-ISO calendar systems in an application introduces significant extra complexity.
 * Ensure that the warnings and recommendations in {@code ChronoLocalDate} have been read before
 * working with the "chrono" interfaces.
 * </p>
 * <p>
 * The supported calendar systems includes:
 * </p>
 * <ul>
 * <li>{@link java.time.chrono.HijrahChronology Hijrah calendar}</li>
 * <li>{@link java.time.chrono.JapaneseChronology Japanese calendar}</li>
 * <li>{@link java.time.chrono.MinguoChronology Minguo calendar}</li>
 * <li>{@link java.time.chrono.ThaiBuddhistChronology Thai Buddhist calendar}</li>
 * </ul>
 *
 * <h2>Example</h2>
 * <p>
 * This example lists today's date for all of the available calendars.
 * </p>
 * <pre>
 *   // Enumerate the list of available calendars and print today's date for each.
 *       Set&lt;Chronology&gt; chronos = Chronology.getAvailableChronologies();
 *       for (Chronology chrono : chronos) {
 *           ChronoLocalDate date = chrono.dateNow();
 *           System.out.printf("   %20s: %s%n", chrono.getId(), date.toString());
 *       }
 * </pre>
 *
 * <p>
 * This example creates and uses a date in a named non-ISO calendar system.
 * </p>
 * <pre>
 *   // Print the Thai Buddhist date
 *       ChronoLocalDate now1 = Chronology.of("ThaiBuddhist").dateNow();
 *       int day = now1.get(ChronoField.DAY_OF_MONTH);
 *       int dow = now1.get(ChronoField.DAY_OF_WEEK);
 *       int month = now1.get(ChronoField.MONTH_OF_YEAR);
 *       int year = now1.get(ChronoField.YEAR);
 *       System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
 *                 dow, day, month, year);
 *   // Print today's date and the last day of the year for the Thai Buddhist Calendar.
 *       ChronoLocalDate first = now1
 *                 .with(ChronoField.DAY_OF_MONTH, 1)
 *                 .with(ChronoField.MONTH_OF_YEAR, 1);
 *       ChronoLocalDate last = first
 *                 .plus(1, ChronoUnit.YEARS)
 *                 .minus(1, ChronoUnit.DAYS);
 *       System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
 *                 first, last);
 *  </pre>
 *
 * <p>
 * This example creates and uses a date in a specific ThaiBuddhist calendar system.
 * </p>
 * <pre>
 *   // Print the Thai Buddhist date
 *       ThaiBuddhistDate now1 = ThaiBuddhistDate.now();
 *       int day = now1.get(ChronoField.DAY_OF_MONTH);
 *       int dow = now1.get(ChronoField.DAY_OF_WEEK);
 *       int month = now1.get(ChronoField.MONTH_OF_YEAR);
 *       int year = now1.get(ChronoField.YEAR);
 *       System.out.printf("  Today is %s %s %d-%s-%d%n", now1.getChronology().getId(),
 *                 dow, day, month, year);
 *
 *   // Print today's date and the last day of the year for the Thai Buddhist Calendar.
 *       ThaiBuddhistDate first = now1
 *                 .with(ChronoField.DAY_OF_MONTH, 1)
 *                 .with(ChronoField.MONTH_OF_YEAR, 1);
 *       ThaiBuddhistDate last = first
 *                 .plus(1, ChronoUnit.YEARS)
 *                 .minus(1, ChronoUnit.DAYS);
 *       System.out.printf("  %s: 1st of year: %s; end of year: %s%n", last.getChronology().getId(),
 *                 first, last);
 *  </pre>
 *
 * <h2>Package specification</h2>
 * <p>
 * Unless otherwise noted, passing a null argument to a constructor or method in any class or interface
 * in this package will cause a {@link java.lang.NullPointerException NullPointerException} to be thrown.
 * The Javadoc "@param" definition is used to summarise the null-behavior.
 * The "@throws {@link java.lang.NullPointerException}" is not explicitly documented in each method.
 * </p>
 * <p>
 * All calculations should check for numeric overflow and throw either an {@link java.lang.ArithmeticException}
 * or a {@link java.time.DateTimeException}.
 * </p>
 * @since 1.8
 */
package java.time.chrono;
