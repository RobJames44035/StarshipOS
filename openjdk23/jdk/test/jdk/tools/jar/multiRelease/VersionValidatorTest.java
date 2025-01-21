/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
* @test
# @bug 8296329
* @summary Tests for version validator.
* @library /test/lib
* @modules java.base/jdk.internal.misc
*          jdk.compiler
*          jdk.jartool
* @build jdk.test.lib.Utils
*        jdk.test.lib.Asserts
*        jdk.test.lib.JDKToolFinder
*        jdk.test.lib.JDKToolLauncher
*        jdk.test.lib.Platform
*        jdk.test.lib.process.*
*        MRTestBase
* @run testng/timeout=1200 VersionValidatorTest
*/

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class VersionValidatorTest extends MRTestBase {
    private Path root;

    @BeforeMethod
    void testInit(Method method) {
        root = Paths.get(method.getName());
    }

    @Test(dataProvider = "differentMajorVersions")
    public void onlyCompatibleVersionIsAllowedInMultiReleaseJar(String baseMajorVersion, String otherMajorVersion,
            boolean enablePreviewForBaseVersion, boolean enablePreviewForOtherVersion, boolean isAcceptable)
            throws Throwable {
        Path baseVersionClassesDir = compileLibClass(baseMajorVersion, enablePreviewForBaseVersion);
        Path otherVersionClassesDir = compileLibClass(otherMajorVersion, enablePreviewForOtherVersion);

        var result = jar("--create", "--file", "lib.jar", "-C", baseVersionClassesDir.toString(), "Lib.class",
                "--release", otherMajorVersion, "-C", otherVersionClassesDir.toString(), "Lib.class");

        if (isAcceptable) {
            result.shouldHaveExitValue(SUCCESS)
                    .shouldBeEmptyIgnoreVMWarnings();
        } else {
            result.shouldNotHaveExitValue(SUCCESS)
                    .shouldContain("has a class version incompatible with an earlier version");
        }
    }

    private Path compileLibClass(String majorVersion, boolean enablePreview) throws Throwable {
        String classTemplate = """
                public class Lib {
                    public static int version = $VERSION;
                }
                    """;

        Path sourceFile = Files.createDirectories(root.resolve("src").resolve(majorVersion)).resolve("Lib.java");
        Files.write(sourceFile, classTemplate.replace("$VERSION", majorVersion).getBytes());

        Path classesDir = root.resolve("classes").resolve(majorVersion);

        javac(Integer.parseInt(majorVersion), classesDir, sourceFile);
        if (enablePreview) {
            rewriteMinorVersionForEnablePreviewClass(classesDir.resolve("Lib.class"));
        }
        return classesDir;
    }

    private void rewriteMinorVersionForEnablePreviewClass(Path classFile) throws Throwable {
        byte[] classBytes = Files.readAllBytes(classFile);
        classBytes[4] = -1;
        classBytes[5] = -1;
        Files.write(classFile, classBytes);
    }

    @DataProvider
    Object[][] differentMajorVersions() {
        return new Object[][] {
                { "19", "20", false, true, true },
                { "19", "20", false, false, true },
                { "19", "20", true, true, true },
                { "19", "20", true, false, true },
                { "20", "19", false, true, false },
                { "20", "19", false, false, false },
                { "20", "19", true, true, false },
                { "20", "19", true, false, false },
        };
    }
}
