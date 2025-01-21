/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jdk.jpackage.test.JPackageCommand;
import jdk.jpackage.test.Executor;
import jdk.jpackage.test.TKit;
import jdk.jpackage.test.JavaTool;
import jdk.jpackage.test.Annotations.Test;
import jdk.jpackage.test.Annotations.Parameter;


/**
 * Tests generation of app image with --mac-app-store and --runtime-image. jpackage should able
 * to generate app image if runtime image does not have "bin" folder and fail otherwise.
 */

/*
 * @test
 * @summary jpackage with --mac-app-store and --runtime-image
 * @library /test/jdk/tools/jpackage/helpers
 * @build jdk.jpackage.test.*
 * @build MacAppStoreRuntimeTest
 * @requires (os.family == "mac")
 * @run main/othervm -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=MacAppStoreRuntimeTest
 */
public class MacAppStoreRuntimeTest {

    private static String getRuntimeImage(boolean stripNativeCommands) throws IOException {
        final Path workDir = TKit.createTempDirectory("runtime").resolve("data");
        final Path jlinkOutputDir = workDir.resolve("temp.runtime");
        Files.createDirectories(jlinkOutputDir.getParent());

        // List of modules required for test app.
        final var modules = new String[] {
            "java.base",
            "java.desktop"
        };

        List<String> jlinkArgs = new ArrayList<>();
        jlinkArgs.add("--output");
        jlinkArgs.add(jlinkOutputDir.toString());
        jlinkArgs.add("--add-modules");
        jlinkArgs.add(String.join(",", modules));
        jlinkArgs.add("--strip-debug");
        jlinkArgs.add("--no-header-files");
        jlinkArgs.add("--no-man-pages");
        if (stripNativeCommands) {
            jlinkArgs.add("--strip-native-commands");
        }

        new Executor()
                .setToolProvider(JavaTool.JLINK)
                .dumpOutput()
                .addArguments(jlinkArgs)
                .execute();

        return jlinkOutputDir.toString();
    }

    @Test
    @Parameter("true")
    @Parameter("false")
    public static void test(boolean stripNativeCommands) throws Exception {
        JPackageCommand cmd = JPackageCommand.helloAppImage();
        cmd.addArguments("--mac-app-store", "--runtime-image", getRuntimeImage(stripNativeCommands));

        if (stripNativeCommands) {
            cmd.executeAndAssertHelloAppImageCreated();
        } else {
            cmd.execute(1);
        }
    }
}
