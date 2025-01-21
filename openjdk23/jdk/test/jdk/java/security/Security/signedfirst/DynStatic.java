/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
/*
 * @test
 * @bug 4504355 4744260
 * @summary problems if signed crypto provider is the most preferred provider
 * @modules java.base/sun.security.tools.keytool
 *          jdk.jartool/sun.security.tools.jarsigner
 * @library /test/lib
 * @run main/othervm DynStatic
 */

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.test.lib.compiler.CompilerUtils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.util.JarUtils;

public class DynStatic {

    private static final String TEST_SRC =
        Paths.get(System.getProperty("test.src")).toString();
    private static final Path TEST_CLASSES =
        Paths.get(System.getProperty("test.classes"));

    private static final Path EXP_SRC_DIR = Paths.get(TEST_SRC, "com");
    private static final Path EXP_DEST_DIR = Paths.get("build");
    private static final Path DYN_SRC =
        Paths.get(TEST_SRC, "DynSignedProvFirst.java");
    private static final Path STATIC_SRC =
        Paths.get(TEST_SRC, "StaticSignedProvFirst.java");
    private static final Path STATIC_PROPS =
        Paths.get(TEST_SRC, "Static.props");

    public static void main(String[] args) throws Exception {

        // Compile the provider
        CompilerUtils.compile(EXP_SRC_DIR, EXP_DEST_DIR);

        // Create a jar file containing the provider
        JarUtils.createJarFile(Path.of("exp.jar"), EXP_DEST_DIR, "com");

        // Create a keystore
        sun.security.tools.keytool.Main.main(
            ("-genkeypair -dname CN=Signer -keystore exp.ks -storepass "
                + "changeit -keypass changeit -keyalg rsa").split(" "));

        // Sign jar
        sun.security.tools.jarsigner.Main.main(
                "-storepass changeit -keystore exp.ks exp.jar mykey"
                        .split(" "));

        // Compile the DynSignedProvFirst test program
        CompilerUtils.compile(DYN_SRC, TEST_CLASSES, "-classpath", "exp.jar");

        // Run the DynSignedProvFirst test program
        ProcessTools.executeTestJava("-classpath",
            TEST_CLASSES.toString() + File.pathSeparator + "exp.jar",
            "DynSignedProvFirst")
            .shouldContain("test passed");

        // Compile the StaticSignedProvFirst test program
        CompilerUtils.compile(STATIC_SRC, TEST_CLASSES, "-classpath", "exp.jar");

        // Run the StaticSignedProvFirst test program
        ProcessTools.executeTestJava("-classpath",
            TEST_CLASSES.toString() + File.pathSeparator + "exp.jar",
            "-Djava.security.properties=" + STATIC_PROPS.toUri(),
            "StaticSignedProvFirst")
            .shouldContain("test passed");
    }
}
