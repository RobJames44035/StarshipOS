/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.nio.file.Path;
import jdk.internal.jimage.BasicImageReader;
import jdk.internal.jimage.ImageLocation;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.util.JarBuilder;

import tests.Helper;
import tests.JImageGenerator;
import tests.Result;

/*
 * @test
 * @bug 8278185
 * @summary Test non-ASCII path in custom JRE
 * @library ../lib
 *          /test/lib
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.*
 * @run main/othervm JImageNonAsciiNameTest
 */

public class JImageNonAsciiNameTest {
    private final static String moduleName = "A_module";
    private final static String packageName = "test.\u3042"; //non-ASCII
    private final static String className = "A";
    private final static String fullName = packageName + "." + className;
    private static Helper helper;

    public static void main(String[] args) throws Exception {
        helper = Helper.newHelper();
        if (helper == null) {
            System.err.println("Test not run");
            return;
        }

        String source =
            "package "+packageName+";" +
            "public class "+className+" {" +
            "    public static void main(String[] args) {}" +
            "}";
        String moduleInfo = "module " + moduleName + " {}";

        // Using InMemory features to avoid generating non-ASCII name file
        byte[] byteA = InMemoryJavaCompiler.compile(fullName, source);
        byte[] byteModule = InMemoryJavaCompiler.compile(
                "module-info", moduleInfo);

        Path jarDir = helper.getJarDir();
        JarBuilder jb = new JarBuilder(
                jarDir.resolve(moduleName + ".jar").toString());
        jb.addEntry(fullName.replace(".","/") + ".class", byteA);
        jb.addEntry("module-info.class", byteModule);
        jb.build();

        Path outDir = helper.createNewImageDir(moduleName);

        Result result = JImageGenerator.getJLinkTask()
                .modulePath(helper.defaultModulePath())
                .output(outDir)
                .addMods(moduleName)
                .call();
        Path testImage = result.assertSuccess();

        BasicImageReader bir = BasicImageReader.open(
                testImage.resolve("lib").resolve("modules"));
        ImageLocation loc = bir.findLocation(moduleName,
                fullName.replace(".","/") + ".class");
        if (loc == null) {
            throw new RuntimeException("Failed to find " +
                    fullName + " in module " +moduleName);
        }
    }
}
