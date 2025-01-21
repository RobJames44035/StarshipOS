/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

/*
 * @test
 * @bug 8276036 8277213 8277441
 * @summary test for the value of full_count in the message of insufficient codecache
 * @requires vm.compMode != "Xint"
 * @library /test/lib
 */
public class CodeCacheFullCountTest {
    public static void main(String args[]) throws Throwable {
        if (args.length == 1) {
            wasteCodeCache();
        } else {
            runTest();
        }
    }

    public static void wasteCodeCache() throws Throwable {
        URL url = CodeCacheFullCountTest.class.getProtectionDomain().getCodeSource().getLocation();

        try {
            for (int i = 0; i < 500; i++) {
                ClassLoader cl = new MyClassLoader(url);
                refClass(cl.loadClass("SomeClass"));
            }
        } catch (Throwable t) {
            // Expose the root cause of the Throwable instance.
            while (t.getCause() != null) {
                t = t.getCause();
            }
            throw t;
        }
    }

    public static void runTest() throws Throwable {
        ProcessBuilder pb = ProcessTools.createTestJavaProcessBuilder(
          "-XX:ReservedCodeCacheSize=2496k", "-XX:-UseCodeCacheFlushing", "-XX:-MethodFlushing", "CodeCacheFullCountTest", "WasteCodeCache");
        OutputAnalyzer oa = ProcessTools.executeProcess(pb);
        // Ignore adapter creation failures
        if (oa.getExitValue() != 0 && !oa.getOutput().contains("Out of space in CodeCache")) {
            oa.reportDiagnosticSummary();
            throw new RuntimeException("VM finished with exit code " + oa.getExitValue());
        }
        String stdout = oa.getStdout();

        Pattern pattern = Pattern.compile("full_count=(\\d)");
        Matcher stdoutMatcher = pattern.matcher(stdout);
        if (stdoutMatcher.find()) {
            int fullCount = Integer.parseInt(stdoutMatcher.group(1));
            if (fullCount == 0) {
                throw new RuntimeException("the value of full_count is wrong.");
            }
        } else {
            throw new RuntimeException("codecache shortage did not occur.");
        }
    }

    private static void refClass(Class clazz) throws Exception {
        Field name = clazz.getDeclaredField("NAME");
        name.setAccessible(true);
        name.get(null);
    }

    private static class MyClassLoader extends URLClassLoader {
        public MyClassLoader(URL url) {
            super(new URL[]{url}, null);
        }
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            try {
                return super.loadClass(name, resolve);
            } catch (ClassNotFoundException e) {
                return Class.forName(name, resolve, CodeCacheFullCountTest.class.getClassLoader());
            }
        }
    }
}

abstract class Foo {
    public abstract int foo();
}

class Foo1 extends Foo {
    private int a;
    public int foo() { return a; }
}

class Foo2 extends Foo {
    private int a;
    public int foo() { return a; }
}

class Foo3 extends Foo {
    private int a;
    public int foo() { return a; }
}

class Foo4 extends Foo {
    private int a;
    public int foo() { return a; }
}

class SomeClass {
    static final String NAME = "name";

    static {
        int res =0;
        Foo[] foos = new Foo[] { new Foo1(), new Foo2(), new Foo3(), new Foo4() };
        for (int i = 0; i < 100000; i++) {
            res = foos[i % foos.length].foo();
        }
    }
}
