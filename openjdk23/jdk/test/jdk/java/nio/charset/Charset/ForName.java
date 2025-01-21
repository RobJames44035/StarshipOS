/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/* @test
 * @summary Unit test for forName(String, Charset)
 * @bug 8270490
 * @modules jdk.charsets
 * @run testng ForName
 */

import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

@Test
public class ForName {

    @DataProvider
    Object[][] params() {
        return new Object[][] {
                {"UTF-8", null, StandardCharsets.UTF_8},
                {"UTF-8", StandardCharsets.US_ASCII, StandardCharsets.UTF_8},
                {"windows-31j", StandardCharsets.US_ASCII, Charset.forName("windows-31j")},
                {"foo", StandardCharsets.US_ASCII, StandardCharsets.US_ASCII},
                {"foo", null, null},
                {"\u3042", null, null},
                {"\u3042", StandardCharsets.UTF_8, StandardCharsets.UTF_8},
        };
    }

    @DataProvider
    Object[][] paramsIAE() {
        return new Object[][] {
                {null, null},
                {null, StandardCharsets.UTF_8},
        };
    }

    @Test(dataProvider="params")
    public void testForName_2arg(String name, Charset fallback, Charset expected) throws Exception {
        var cs = Charset.forName(name, fallback);
        assertEquals(cs, expected);
    }

    @Test(dataProvider="paramsIAE", expectedExceptions=IllegalArgumentException.class)
    public void testForName_2arg_IAE(String name, Charset fallback) throws Exception {
        Charset.forName(name, fallback);
    }
}
