/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug 7067922
 * @author sogoel
 * @summary Test negative scenarios for main class attribute
 * @modules jdk.compiler
 *          jdk.zipfs
 * @build MainClassAttributeTest
 * @run main MainClassAttributeTest
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * This tests negative scenarios for Main class entry in a jar file.
 * An error should be thrown for each of the test cases when such a
 * jar is executed.
 */

public class MainClassAttributeTest extends TestHelper {

    /*
     * These tests compare messages which could be localized, therefore
     * these tests compare messages only with English locales, and
     * for all other locales,  the exit values are checked.
     */
    static void runTest(File jarFile, String expectedErrorMessage) {
        TestResult tr = doExec(TestHelper.javaCmd,
                "-jar", jarFile.getAbsolutePath());
        if (isEnglishLocale() && !tr.contains(expectedErrorMessage)) {
            System.out.println(tr);
            throw new RuntimeException("expected string not found");
        }
        if (tr.isOK()) {
            System.out.println(tr);
            throw new RuntimeException("test exit with status 0");
        }
    }

    // Missing manifest entry
    static void test1() throws IOException {
        File jarFile = new File("missingmainentry.jar");
        createJar("cvf", jarFile.getName(), ".");
        runTest(jarFile, "no main manifest attribute");
    }

    // Entry point in manifest file has .class extension
    static void test2() throws IOException {
        File jarFile = new File("extensionmainentry.jar");
        createJar("Foo.class", jarFile, new File("Foo"), (String[])null);
        runTest(jarFile, "Error: Could not find or load main class");
    }

    // Entry point in manifest file is misspelled
    static void test3() throws IOException {
        File jarFile = new File("misspelledmainentry.jar");
        createJar("FooMIS", jarFile, new File("Foo"), (String[])null);
        runTest(jarFile, "Error: Could not find or load main class");
    }

    // Main-Class attribute is misspelled in manifest file
    static void test4() throws IOException {
        File jarFile = new File("misspelledMainAttribute.jar");
        File manifestFile = new File("manifest.txt");
        List<String> contents = new ArrayList<>();
        contents.add("MainClassName: Foo");
        createFile(manifestFile, contents);
        createJar("-cmf", manifestFile.getName(), jarFile.getName());
        runTest(jarFile, "no main manifest attribute");
    }

    public static void main(String[] args) throws IOException {
        test1();
        test2();
        test3();
        test4();
    }
}
