/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/**
 * @test
 * @bug 4806786 8023113
 * @modules jdk.jartool
 * @summary jar -C doesn't ignore multiple // in path
 */

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import java.util.spi.ToolProvider;
import java.util.stream.Stream;

public class ChangeDir {
    private static final ToolProvider JAR_TOOL = ToolProvider.findFirst("jar")
        .orElseThrow(() ->
            new RuntimeException("jar tool not found")
        );

    private final static String jarName = "test.jar";
    private final static String fileName = "hello.txt";

    /** Remove dirs & files needed for test. */
    private static void cleanup(Path dir) {
        try {
            if (Files.isDirectory(dir)) {
                try (Stream<Path> s = Files.list(dir)) {
                    s.forEach( p -> cleanup(p));
                }
            }
            Files.delete(dir);
        } catch (IOException x) {
            fail(x.toString());
        }
    }

    public static void realMain(String[] args) throws Throwable {
        doTest("/");
        doTest("//");
        doTest("///");
        doTest("////");
        if (System.getProperty("os.name").startsWith("Windows")) {
            doTest("\\");
            doTest("\\\\");
            doTest("\\\\\\");
            doTest("\\\\\\\\");
            doTest("\\/");
        }
    }

    static void doTest(String sep) throws Throwable {
        Path topDir = Files.createTempDirectory("delete");
        try {
            Files.deleteIfExists(Paths.get(jarName));

            // Create a subdirectory "a/b"
            Path testDir = Files.createDirectories(topDir.resolve("a").resolve("b"));

            // Create file in that subdirectory
            Path testFile = testDir.resolve(fileName);
            Files.createFile(testFile);

            // Create a jar file from that subdirectory, but with a // in the
            // path  name.
            List<String> argList = new ArrayList<String>();
            argList.add("cf");
            argList.add(jarName);
            argList.add("-C");
            argList.add(topDir.toString() + sep + "a" + sep + sep + "b"); // Note double 'sep' is intentional
            argList.add(fileName);

            int rc = JAR_TOOL.run(System.out, System.err,
                                  argList.toArray(new String[argList.size()]));
            if (rc != 0) {
                fail("Could not create jar file.");
            }

            // Check that the entry for hello.txt does *not* have a pathname.
            try (JarFile jf = new JarFile(jarName)) {
                for (Enumeration<JarEntry> i = jf.entries(); i.hasMoreElements();) {
                    JarEntry je = i.nextElement();
                    String name = je.getName();
                    if (name.indexOf(fileName) != -1) {
                        if (name.indexOf(fileName) != 0) {
                            fail(String.format(
                                     "Expected '%s' but got '%s'%n", fileName, name));
                        } else {
                            pass();
                        }
                    }
                }
            }
        } finally {
            cleanup(topDir);
            Files.deleteIfExists(Paths.get(jarName));
        }
    }

    //--------------------- Infrastructure ---------------------------
    static volatile int passed = 0, failed = 0;
    static void pass() {passed++;}
    static void fail() {failed++; Thread.dumpStack();}
    static void fail(String msg) {System.out.println(msg); fail();}
    static void unexpected(Throwable t) {failed++; t.printStackTrace();}
    static void check(boolean cond) {if (cond) pass(); else fail();}
    static void equal(Object x, Object y) {
        if (x == null ? y == null : x.equals(y)) pass();
        else fail(x + " not equal to " + y);}
    public static void main(String[] args) throws Throwable {
        try {realMain(args);} catch (Throwable t) {unexpected(t);}
        System.out.println("\nPassed = " + passed + " failed = " + failed);
        if (failed > 0) throw new AssertionError("Some tests failed");}
}
