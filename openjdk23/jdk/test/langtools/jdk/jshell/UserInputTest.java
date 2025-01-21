/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8131023 8167461
 * @summary Verify that the user's code can read System.in
 * @build KullaTesting TestingInputStream
 * @run testng UserInputTest
 * @key intermittent
 */

import java.io.IOException;
import java.io.InputStream;

import org.testng.annotations.Test;

@Test
public class UserInputTest extends KullaTesting {

    public void testReadInput() {
        setInput("AB\n");
        assertEval("System.in.read()", "65");
        setInput("CD\n");
        assertEval("System.in.read()", "67");
    }

    public void testScanner() {
        assertEval("import java.util.Scanner;");
        assertEval("Scanner s = new Scanner(System.in);");
        setInput("12\n");
        assertEval("s.nextInt();", "12");
    }

    public void testClose() {
        setInput(new InputStream() {
            private final byte[] data = new byte[] {0, 1, 2};
            private int cursor;
            @Override public int read() throws IOException {
                if (cursor < data.length) {
                    return data[cursor++];
                } else {
                    return -1;
                }
            }
        });
        assertEval("int read;", "0");
        assertEval("System.in.read();", "0");
        assertEval("System.in.read();", "1");
        assertEval("System.in.read();", "2");
        assertEval("System.in.read();", "-1");
        assertEval("System.in.read();", "-1");
        assertEval("System.in.read();", "-1");
    }

    public void testException() {
        setInput(new InputStream() {
            private final int[] data = new int[] {0, 1, -2, 2};
            private int cursor;
            @Override public int read() throws IOException {
                if (cursor < data.length) {
                    int d = data[cursor++];
                    if (d == (-2)) {
                        throw new IOException("Crashed");
                    }
                    return d;
                } else {
                    return -1;
                }
            }
        });
        assertEval("int read;", "0");
        assertEval("System.in.read();", "0");
        assertEval("System.in.read();", "1");
        assertEval("java.io.IOException e;");
        assertEval("try { System.in.read(); } catch (java.io.IOException exc) { e = exc; }");
        assertEval("e", "java.io.IOException: Crashed");
        assertEval("System.in.read();", "2");
        assertEval("System.in.read();", "-1");
    }

    public void testNoConsole() {
        assertEval("System.console()", "null");
    }
}
