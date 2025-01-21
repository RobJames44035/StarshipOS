/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.value;

import org.junit.Assert;
import org.junit.Test;

public class DefaultParserTest {
    @Test
    public void testParseStringArray() throws Exception {
        DefaultParser parser = new DefaultParser();
        String line = "a aa   aaa";
        String[] result = {"a", "aa", "", "", "aaa"};
        Assert.assertArrayEquals(result,
                (Object[]) parser.parse(result.getClass(), line, " "));

        line = null;
        result = new String[]{};
        Assert.assertArrayEquals(result,
                (Object[]) parser.parse(result.getClass(), line, " "));
    }

    @Test
    public void testParseObjectArray() throws Exception {
        DefaultParser parser = new DefaultParser();
        String line = "a aa   aaa";
        String[] result = {"a", "aa", "", "", "aaa"};
        Assert.assertArrayEquals(result,
                (String[]) parser.parse(result.getClass(), line, " "));
        Object[] result2 = {"a", "aa", "", "", "aaa"};
        Assert.assertArrayEquals(result2,
                (Object[]) parser.parse(result.getClass(), line, " "));
    }

    @Test
    public void testParseCharArray() throws Exception {
        DefaultParser parser = new DefaultParser();
        String line = "a b c a";
        char[] result = {'a', 'b', 'c', 'a'};
        Assert.assertArrayEquals(result,
                (char[]) parser.parse(result.getClass(), line, " "));

        Character[] result2 = {'a', 'b', 'c', 'a'};
        Assert.assertArrayEquals(result2,
                (Character[]) parser.parse(result2.getClass(), line, " "));
    }

    @Test
    public void testParseBoolean() throws Exception {
        DefaultParser parser = new DefaultParser();
        String line = "a b c a";
        Assert.assertEquals(false,
                (boolean) parser.parse(boolean.class, line, " "));
        Assert.assertEquals(Boolean.FALSE,
                parser.parse(Boolean.class, line, " "));
        line = "trUe";
        Assert.assertEquals(true,
                (boolean) parser.parse(boolean.class, line, " "));
        Assert.assertEquals(Boolean.TRUE,
                parser.parse(Boolean.class, line, " "));
    }

    @Test
    public void testParseShort() throws Exception {
        DefaultParser parser = new DefaultParser();
        Assert.assertSame("10", (short) 10,
                parser.parse(short.class, "10", " "));
        Assert.assertSame("010", (short) 8,
                parser.parse(short.class, "010", " "));
        Assert.assertSame("0x10", (short) 16,
                parser.parse(short.class, "0x10", " "));
    }

    @Test
    public void testParseByte() throws Exception {
        DefaultParser parser = new DefaultParser();
        Assert.assertSame("11", (byte) 11,
                parser.parse(byte.class, "11", " "));
        Assert.assertSame("011", (byte) 9,
                parser.parse(byte.class, "011", " "));
        Assert.assertSame("0x11", (byte) 17,
                parser.parse(byte.class, "0x11", " "));
    }

    @Test
    public void testParseInt() throws Exception {
        DefaultParser parser = new DefaultParser();
        Assert.assertEquals("20", (int) 20,
                parser.parse(int.class, "20", " "));
        Assert.assertEquals("020", (int) 16,
                parser.parse(int.class, "020", " "));
        Assert.assertEquals("0x20", (int) 32,
                parser.parse(int.class, "0x20", " "));
    }


}
