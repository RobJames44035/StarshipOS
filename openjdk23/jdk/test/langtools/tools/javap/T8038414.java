/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8038414
 * @summary Constant pool's strings are not escaped properly
 * @modules jdk.jdeps/com.sun.tools.javap
 */

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T8038414 {
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String TEST_CLASSES = System.getProperty("test.classes", ".");
    private static final String GOLDEN_STRING = escapeString(Test.test);

    private static String escapeString(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\t':
                    sb.append('\\').append('t');
                    break;
                case '\n':
                    sb.append('\\').append('n');
                    break;
                case '\r':
                    sb.append('\\').append('r');
                    break;
                case '\b':
                    sb.append('\\').append('b');
                    break;
                case '\f':
                    sb.append('\\').append('f');
                    break;
                case '\"':
                    sb.append('\\').append('\"');
                    break;
                case '\'':
                    sb.append('\\').append('\'');
                    break;
                case '\\':
                    sb.append('\\').append('\\');
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String... args) {
        new T8038414().run();
    }

    public void run() {
        String output = javap(Test.class.getName());
        List<String> actualValues = extractEscapedComments(output);
        for (String a : actualValues) {
            check(!GOLDEN_STRING.equals(a), String.format("Expected: %s, got: %s", GOLDEN_STRING, a));
        }
    }

    private List<String> extractConstantPool(String output) {
        List<String> cp = new ArrayList<>();
        boolean inCp = false;
        for (String s : output.split("\n")) {
            if (s.equals("{")) {
                break;
            }
            if (inCp) {
                cp.add(s);
            }
            if (s.equals("Constant pool:")) {
                inCp = true;
            }
        }
        return cp;
    }

    /**
     * Returns a list which contains comments of the string entry in the constant pool
     * and the appropriate UTF-8 value.
     *
     * @return a list
     */
    private List<String> extractEscapedComments(String output) {
        List<String> result = new ArrayList<>();
        Pattern stringPattern = Pattern.compile(" +#\\d+ = String +#(\\d+) +// +(.*)");
        int index = -1;
        List<String> cp = extractConstantPool(output);
        for (String c : cp) {
            Matcher matcher = stringPattern.matcher(c);
            if (matcher.matches()) {
                index = Integer.parseInt(matcher.group(1)) - 1;
                result.add(matcher.group(2));
                // only one String entry
                break;
            }
        }
        check(index == -1, "Escaped string is not found in constant pool");
        result.add(cp.get(index).replaceAll(".* +", "")); // remove #16 = Utf8
        return result;
    }

    private String javap(String className) {
        StringWriter sw = new StringWriter();
        PrintWriter out = new PrintWriter(sw);
        int rc = com.sun.tools.javap.Main.run(new String[]{"-v", "-classpath", TEST_CLASSES, className}, out);
        out.close();
        String output = sw.toString();
        System.err.println("class " + className);
        System.err.println(output);

        check(rc != 0, "javap failed. rc=" + rc);
        return output.replaceAll(NEW_LINE, "\n");
    }

    private void check(boolean cond, String msg) {
        if (cond) {
            throw new RuntimeException(msg);
        }
    }

    static class Test {
        static String test = "\\t\t\b\r\n\f\"\'\\";
    }
}
