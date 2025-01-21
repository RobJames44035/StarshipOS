/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @test
 * @bug 8183743
 * @summary Test to verify the new overload method with Charset functions the same
 * as the existing method that takes a charset name.
 * @run testng EncodingTest
 */
public class EncodingTest {
    public static enum ParameterType {
        STRING,
        CHARSET
    }

    @DataProvider(name = "encode")
    public Object[][] getDecodeParameters() {
        return new Object[][]{
            {"The string \u00FC@foo-bar"},
            // the string from javadoc example

            {""}, // an empty string

            {"x"}, // a string of length 1

            {"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-.*"},
            // the string of characters should remain the same

            {charactersRange('\u0000', '\u007F')},
            // a string of characters from 0 to 127

            {charactersRange('\u0080', '\u00FF')},
            // a string of characters from 128 to 255

            {"\u0100 \u0101 \u0555 \u07FD \u07FF"},
            // a string of Unicode values can be expressed as 2 bytes

            {"\u8000 \u8001 \uA000 \uFFFD \uFFFF"}, // a string of Unicode values can be expressed as 3 bytes
        };
    }

    /**
     * Verifies that the new overload method returns the same result as the
     * existing method.
     *
     * @param s the string to be encoded
     * @throws Exception if the test fails
     */
    @Test(dataProvider = "encode")
    public void encode(String s) throws Exception {
        String encoded1 = URLEncoder.encode(s, StandardCharsets.UTF_8.name());
        String encoded2 = URLEncoder.encode(s, StandardCharsets.UTF_8);
        Assert.assertEquals(encoded1, encoded2);

        // cross check
        String returned1 = URLDecoder.decode(encoded1, StandardCharsets.UTF_8.name());
        String returned2 = URLDecoder.decode(encoded2, StandardCharsets.UTF_8);
        Assert.assertEquals(returned1, returned2);
    }

    String charactersRange(char c1, char c2) {
        StringBuilder sb = new StringBuilder(c2 - c1);
        for (char c = c1; c < c2; c++) {
            sb.append(c);
        }

        return sb.toString();
    }
}
