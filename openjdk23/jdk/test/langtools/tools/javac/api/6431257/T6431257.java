/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     6431257
 * @summary JSR 199: Changes to JavaFileManager to support JSR 269 Filer API
 * @author  Peter von der Ah\u00e9
 * @library ../lib
 * @modules java.compiler
 *          jdk.compiler
 * @build ToolTester
 * @compile T6431257.java package-info.java
 * @run main T6431257 foo.bar.baz foo/bar/baz
 */

import java.io.IOException;
import java.util.EnumSet;
import javax.tools.*;
import static javax.tools.JavaFileObject.Kind.CLASS;
import static javax.tools.StandardLocation.CLASS_OUTPUT;
import static javax.tools.StandardLocation.PLATFORM_CLASS_PATH;

public class T6431257 extends ToolTester {
    void test(String... args) throws IOException {
        for (String arg : args)
            testPackage(arg);
    }

    void testPackage(String packageName) throws IOException {
        JavaFileObject object
            = fm.getJavaFileForInput(PLATFORM_CLASS_PATH, "java.lang.Object", CLASS);
        Iterable<? extends JavaFileObject> files
            = fm.list(CLASS_OUTPUT, packageName, EnumSet.of(CLASS), false);
        boolean found = false;
        String binaryPackageName = packageName.replace('/', '.');
        for (JavaFileObject file : files) {
            System.out.println("Found " + file.getName() + " in " + packageName);
            String name = fm.inferBinaryName(CLASS_OUTPUT, file);
            found |= name.equals(binaryPackageName + ".package-info");
            JavaFileObject other = fm.getJavaFileForInput(CLASS_OUTPUT, name, CLASS);
            if (!fm.isSameFile(file, other))
                throw new AssertionError(file + " != " + other);
            if (fm.isSameFile(file, object))
                throw new AssertionError(file + " == " + object);
        }
        if (!found)
            throw new AssertionError("Did not find " + binaryPackageName + ".package-info");
    }

    public static void main(String... args) throws IOException {
        try (T6431257 t = new T6431257()) {
            t.test(args);
        }
    }
}
