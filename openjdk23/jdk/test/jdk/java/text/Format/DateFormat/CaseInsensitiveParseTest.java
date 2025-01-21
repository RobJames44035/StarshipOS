/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/**
 * @test
 * @bug 8248434
 * @modules jdk.localedata
 * @run testng/othervm CaseInsensitiveParseTest
 * @summary Checks format/parse round trip in case-insensitive manner.
 */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CaseInsensitiveParseTest {

    private final static String PATTERN = "GGGG/yyyy/MMMM/dddd/hhhh/mmmm/ss/aaaa";
    private final static Date EPOCH = new Date(0L);

    @DataProvider
    private Object[][] locales() {
        return (Object[][])Arrays.stream(DateFormat.getAvailableLocales())
            .map(Stream::of)
            .map(Stream::toArray)
            .toArray(Object[][]::new);
    }

    @Test(dataProvider = "locales")
    public void testUpperCase(Locale loc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, loc);
        String formatted = sdf.format(EPOCH);
        assertEquals(sdf.parse(formatted.toUpperCase(Locale.ROOT)), EPOCH,
                "roundtrip failed for string '" + formatted + "', locale: " + loc);
    }

    @Test(dataProvider = "locales")
    public void testLowerCase(Locale loc) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN, loc);
        String formatted = sdf.format(EPOCH);
        assertEquals(sdf.parse(formatted.toLowerCase(Locale.ROOT)), EPOCH,
                "roundtrip failed for string '" + formatted + "', locale: " + loc);
    }
}
