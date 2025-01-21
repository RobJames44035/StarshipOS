/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.share;
import java.io.UnsupportedEncodingException;

public abstract class AbstractGoldChecker {

    private final StringBuffer sb = new StringBuffer();

    protected abstract String getGoldenString();

    public void print(boolean b) {
        sb.append(String.valueOf(b));
    }

    public void print(byte b) {
        sb.append(String.valueOf(b));
    }

    public void print(char c) {
        sb.append(String.valueOf(c));
    }

    public void print(int i) {
        sb.append(String.valueOf(i));
    }

    public void print(long l) {
        sb.append(String.valueOf(l));
    }

    public void print(float f) {
        sb.append(String.valueOf(f));
    }

    public void print(double d) {
        sb.append(String.valueOf(d));
    }

    public void print(String s) {
        sb.append(s);
    }

    public void println() {
        sb.append('\n');
    }

    public void println(boolean b) {
        sb.append(String.valueOf(b));
        sb.append('\n');
    }

    public void println(byte b) {
        sb.append(String.valueOf(b));
        sb.append('\n');
    }

    public void println(char c) {
        sb.append(String.valueOf(c));
        sb.append('\n');
    }

    public void println(int i) {
        sb.append(String.valueOf(i));
        sb.append('\n');
    }

    public void println(long l) {
        sb.append(String.valueOf(l));
        sb.append('\n');
    }

    public void println(float f) {
        sb.append(String.valueOf(f));
        sb.append('\n');
    }

    public void println(double d) {
        sb.append(String.valueOf(d));
        sb.append('\n');
    }

    public void println(String s) {
        sb.append(s);
        sb.append('\n');
    }

    public void check() {
        String testOutput;
        try {
            testOutput = new String(sb.toString().getBytes("US-ASCII"), "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new TestFailure(e);
        }

        String goldOutput = getGoldenString();
        if (!compare(testOutput, goldOutput)) {
            throw new TestFailure(
                "Gold comparison failed\n" +
                "\n" +
                "Test output:\n" +
                "============\n" +
                "\n" +
                testOutput +
                "\n" +
                "------------\n" +
                "\n" +
                "Gold output:\n" +
                "============\n" +
                "\n" +
                goldOutput +
                "\n" +
                "------------\n" +
                "\n"
           );
        }
    }

    public boolean compare(String src, String dst) {
        int i1 = 0;
        int i2 = 0;

        int src_len = src.length();
        int dst_len = dst.length();

        while ((i1 < src_len) && (i2 < dst_len)) {

            char c1 = src.charAt(i1++);
            if ((c1 == '\r') && (i1 < src_len)) {
                c1 = src.charAt(i1++);
            }

            char c2 = dst.charAt(i2++);
            if ((c2 == '\r') && (i2 < dst_len)) {
                c2 = dst.charAt(i2++);
            }

            if (c1 != c2) {
                return false;
            }
        }
        return (i1 == src_len) && (i2 == dst_len);
    }
}
