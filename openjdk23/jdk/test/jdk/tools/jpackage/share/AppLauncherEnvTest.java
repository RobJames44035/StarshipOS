/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Executor;
import static jdk.jpackage.test.HelloApp.configureEnvironment;
import jdk.jpackage.test.TKit;

/**
 * Tests values of environment variables altered by jpackage launcher.
 */

/*
 * @test
 * @summary Tests values of environment variables altered by jpackage launcher
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @build AppLauncherEnvTest
 * @run main/othervm -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=AppLauncherEnvTest
 */
public class AppLauncherEnvTest {

    @Test
    public static void test() throws Exception {
        final String testAddDirProp = "jpackage.test.AppDir";

        JPackageCommand cmd = JPackageCommand
                .helloAppImage(TEST_APP_JAVA + "*Hello")
                .ignoreFakeRuntime()
                .addArguments("--java-options", "-D" + testAddDirProp
                        + "=$APPDIR");

        cmd.executeAndAssertImageCreated();

        final String envVarName = envVarName();

        final int attempts = 3;
        final int waitBetweenAttemptsSeconds = 5;
        List<String> output = configureEnvironment(new Executor())
                .saveOutput()
                .setExecutable(cmd.appLauncherPath().toAbsolutePath())
                .addArguments("--print-env-var=" + envVarName)
                .addArguments("--print-sys-prop=" + testAddDirProp)
                .addArguments("--print-sys-prop=" + "java.library.path")
                .executeAndRepeatUntilExitCode(0, attempts,
                        waitBetweenAttemptsSeconds).getOutput();

        BiFunction<Integer, String, String> getValue = (idx, name) -> {
            return  output.get(idx).substring((name + "=").length());
        };

        final String actualEnvVarValue = getValue.apply(0, envVarName);
        final String appDir = getValue.apply(1, testAddDirProp);

        final String expectedEnvVarValue = Optional.ofNullable(System.getenv(
                envVarName)).orElse("") + File.pathSeparator + appDir;

        TKit.assertTextStream(expectedEnvVarValue)
            .predicate(TKit.isLinux() ? String::endsWith : String::equals)
            .label(String.format("value of %s env variable", envVarName))
            .apply(Stream.of(actualEnvVarValue));

        final String javaLibraryPath = getValue.apply(2, "java.library.path");
        TKit.assertTrue(
                List.of(javaLibraryPath.split(File.pathSeparator)).contains(
                        appDir), String.format(
                        "Check java.library.path system property [%s] contains app dir [%s]",
                        javaLibraryPath, appDir));
    }

    private static String envVarName() {
        if (TKit.isLinux()) {
            return "LD_LIBRARY_PATH";
        } else if (TKit.isWindows()) {
            return "PATH";
        } else if (TKit.isOSX()) {
            return "DYLD_LIBRARY_PATH";
        } else {
            throw new IllegalStateException();
        }
    }

    private static final Path TEST_APP_JAVA = TKit.TEST_SRC_ROOT.resolve(
            "apps/PrintEnv.java");
}
