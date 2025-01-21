/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 4673940 4930794 8211842 6914801
 * @summary Unit tests for inetd feature
 * @requires (os.family == "linux" | os.family == "mac")
 * @library /test/lib
 * @build jdk.test.lib.Utils
 *        jdk.test.lib.Asserts
 *        jdk.test.lib.JDKToolFinder
 *        jdk.test.lib.JDKToolLauncher
 *        jdk.test.lib.Platform
 *        jdk.test.lib.process.*
 *        UnixSocketTest StateTest StateTestService EchoTest EchoService
 *        UnixDomainChannelTest CloseTest Launcher Util
 *        CheckIPv6Test CheckIPv6Service
 * @run testng/othervm/native InheritedChannelTest
 * @key intermittent
 */

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.Utils;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.Platform;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.util.Arrays.asList;

public class InheritedChannelTest {

    private static final String TEST_SRC = System.getProperty("test.src");
    private static final String TEST_CLASSPATH = System.getProperty("test.class.path");
    private static final String TEST_CLASSES = System.getProperty("test.classes");

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

    private static final String ARCH = System.getProperty("os.arch");
    private static final String OS_ARCH = ARCH.equals("i386") ? "i586" : ARCH;

    private static final Path libraryPath
            = Paths.get(System.getProperty("java.library.path"));

    @DataProvider
    public Object[][] testCases() {
        return new Object[][] {
            { "UnixDomainChannelTest", List.of(UnixDomainChannelTest.class.getName())},
            { "UnixSocketTest", List.of(UnixSocketTest.class.getName())},
            { "StateTest", List.of(StateTest.class.getName(), "-Dtest.classes="+TEST_CLASSES)},
            { "EchoTest",  List.of(EchoTest.class.getName())  },
            { "CheckIPv6Test",  List.of(CheckIPv6Test.class.getName())  },
            { "CloseTest", List.of(CloseTest.class.getName()) },
        };
    }

    @Test(dataProvider = "testCases", timeOut=30000)
    public void test(String desc, List<String> opts) throws Throwable {
        String pathVar = Platform.sharedLibraryPathVariableName();
        System.out.println(pathVar + "=" + libraryPath);

        List<String> args = new ArrayList<>();
        args.add(JDKToolFinder.getJDKTool("java"));
        args.addAll(asList(Utils.getTestJavaOpts()));
        args.addAll(List.of("--add-opens", "java.base/java.io=ALL-UNNAMED",
                            "--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED"));
        args.addAll(opts);

        ProcessBuilder pb = new ProcessBuilder(args);

        Map<String, String> env = pb.environment();
        env.put("CLASSPATH", TEST_CLASSPATH);
        env.put(pathVar, libraryPath.toString());

        ProcessTools.executeCommand(pb).shouldHaveExitValue(0);
    }
}
