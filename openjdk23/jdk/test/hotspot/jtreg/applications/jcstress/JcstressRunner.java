/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package applications.jcstress;

import jdk.test.lib.Utils;
import jdk.test.lib.artifacts.Artifact;
import jdk.test.lib.artifacts.ArtifactResolver;
import jdk.test.lib.artifacts.ArtifactResolverException;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

/**
 * jcstress tests wrapper
 */
@Artifact(organization = "org.openjdk.jcstress", name = "jcstress-tests-all",
        revision = JcstressRunner.VERSION, extension = "jar", unpack = false)
public class JcstressRunner {

    public static final String VERSION = "0.17-SNAPSHOT-20240328";
    public static final String MAIN_CLASS = "org.openjdk.jcstress.Main";

    public static final String TIME_BUDGET_PROPERTY = "jcstress.time_budget";
    public static String timeBudget = "6m";

    public static Path pathToArtifact() {
        Map<String, Path> artifacts;
        try {
            artifacts = ArtifactResolver.resolve(JcstressRunner.class);
        } catch (ArtifactResolverException e) {
            throw new Error("TESTBUG: Can not resolve artifacts for "
                            + JcstressRunner.class.getName(), e);
        }
        return artifacts.get("org.openjdk.jcstress.jcstress-tests-all-" + VERSION)
                        .toAbsolutePath();
    }

    public static void main(String[] args) throws Throwable {
        if (args.length < 1) {
            throw new Error("Usage: [jcstress flag]*");
        }
        Path out = Paths.get("jcstress.out").toAbsolutePath();

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(getCmd(args))
                                        .redirectErrorStream(true)
                                        .redirectOutput(out.toFile());
        OutputAnalyzer oa = ProcessTools.executeProcess(pb);
        if (0 != oa.getExitValue()) {
            String message = "jctress test finished with nonzero exitcode "
                              + oa.getExitValue();
            System.err.println(message);

            System.err.print("cmd = ");
            System.err.println(pb.command());

            System.err.print("cout/cerr(");
            System.err.print(out.toString());
            System.err.println(")[");
            Files.lines(out).forEach(System.err::println);
            System.err.println("]cout/cerr");
            throw new Error(message);
        }
    }

    private static String[] getCmd(String[] args) {
        List<String> extraFlags = new ArrayList<>();

        // java.io.tmpdir is set for both harness and forked VM so temporary files
        // created like this File.createTempFile("jcstress", "stdout");
        // don't pollute temporary directories
        extraFlags.add("-Djava.io.tmpdir=" + System.getProperty("user.dir"));

        // add jar with jcstress tests and harness to CP
        extraFlags.add("-cp");
        extraFlags.add(System.getProperty("java.class.path")
                       + File.pathSeparator
                       + pathToArtifact().toString());

        extraFlags.add(MAIN_CLASS);

        extraFlags.add("--jvmArgs");
        extraFlags.add("-Djava.io.tmpdir=" + System.getProperty("user.dir"));

        for (String jvmArg : Utils.getTestJavaOpts()) {
            if (jvmArg.startsWith("-D" + TIME_BUDGET_PROPERTY)) {
                timeBudget = jvmArg.split("=", 2)[1];
            } else {
                extraFlags.add("--jvmArgs");
                extraFlags.add(jvmArg);
            }
        }

        extraFlags.add("-tb");
        extraFlags.add(timeBudget);

        extraFlags.add("-sc");
        extraFlags.add("false");

        extraFlags.add("-af");
        extraFlags.add("GLOBAL");

        String[] result = new String[extraFlags.size() + args.length];
        extraFlags.toArray(result);
        System.arraycopy(args, 0, result, extraFlags.size(), args.length);
        return result;
    }
}
