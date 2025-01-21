/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 4761384
 * @compile -XDignore.symbol.file I18NTest.java
 * @run main I18NTest
 * @summary Test to see if class files with non-ASCII characters can be run
 * @author Joseph D. Darcy, Kumar Srinivasan
 */


import java.util.ArrayList;
import java.io.File;
import java.util.List;

public class I18NTest extends TestHelper {
    static String fileName = null;
    public static void main(String... args) throws Exception {
        String defaultEncoding = System.getProperty("file.encoding");
        if (defaultEncoding == null) {
            System.err.println("Default encoding not found; Error.");
            return;
        }
        if (!defaultEncoding.equals("Cp1252")) {
            System.err.println("Warning: required encoding not found, test skipped.");
            return;
        }
        // for some reason the shell test version insisted on cleaning out the
        // directory, likely being pedantic.
        File cwd = new File(".");
        for (File f : cwd.listFiles(createFilter(CLASS_FILE_EXT))) {
            f.delete();
        }
        for (File f : cwd.listFiles(createFilter(JAVA_FILE_EXT))) {
            f.delete();
        }
        createPlatformFile();

        // compile the generate code using the javac compiler vs. the api, to
        // as a bonus point to see if the argument is passed correctly
        TestResult tr;
        tr = doExec(javacCmd, fileName + JAVA_FILE_EXT);
        if (!tr.isOK()) {
            System.out.println(tr);
            throw new Error("compilation failed...");
        }
        tr = doExec(javaCmd, "-cp", ".", fileName);
        if (!tr.isOK()) {
            System.out.println(tr);
            throw new RuntimeException("run failed with encoding " + defaultEncoding);
        }
    }

    public static void createPlatformFile() throws Exception {
        List<String> buffer = new ArrayList<>();
        // "HelloWorld" with an accented e
        fileName = "i18nH\u00e9lloWorld";
        buffer.clear();
        buffer.add("public class i18nH\u00e9lloWorld {");
        buffer.add("    public static void main(String [] argv) {");
        buffer.add("        System.out.println(\"Hello Cp1252 World\");");
        buffer.add("    }");
        buffer.add("}");
        File outFile = new File(fileName + JAVA_FILE_EXT);
        createFile(outFile, buffer);
    }
}
