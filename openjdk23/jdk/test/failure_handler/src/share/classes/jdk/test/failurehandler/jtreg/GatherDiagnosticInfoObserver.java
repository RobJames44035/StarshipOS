/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.jtreg;

import com.sun.javatest.Harness;
import com.sun.javatest.Parameters;
import com.sun.javatest.TestResult;
import com.sun.javatest.regtest.config.RegressionParameters;
import jdk.test.failurehandler.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The jtreg test execution observer, which gathers info about
 * system and dumps it to a file.
 */
public class GatherDiagnosticInfoObserver implements Harness.Observer {
    public static final String LOG_FILENAME = "environment.log";
    public static final String ENVIRONMENT_OUTPUT = "environment.html";
    public static final String CORES_OUTPUT = "cores.html";


    private Path compileJdk;
    private Path testJdk;

    /*
     * The harness calls this method after each test.
     */
    @Override
    public void finishedTest(TestResult tr) {
        if (!tr.getStatus().isError() && !tr.getStatus().isFailed()) {
            return;
        }

        String jtrFile = tr.getFile().toString();
        final Path workDir = Paths.get(
                jtrFile.substring(0, jtrFile.lastIndexOf('.')));
        workDir.toFile().mkdir();

        String name = getClass().getName();
        PrintWriter log1;
        boolean needClose = false;
        try {
            log1 = new PrintWriter(new FileWriter(
                    workDir.resolve(LOG_FILENAME).toFile(), true), true);
            needClose = true;
        } catch (IOException e) {
            log1 = new PrintWriter(System.out);
            log1.printf("ERROR: %s cannot open log file %s", name,
                    LOG_FILENAME);
            e.printStackTrace(log1);
        }
        final PrintWriter log = log1;
        try {
            log.printf("%s ---%n", name);
            GathererFactory gathererFactory = new GathererFactory(
                    OS.current().family, workDir, log,
                    testJdk, compileJdk);
            gatherEnvInfo(workDir, name, log,
                    gathererFactory.getEnvironmentInfoGatherer());
            Files.walk(workDir)
                    .filter(Files::isRegularFile)
                    .filter(f -> (f.getFileName().toString().contains("core") || f.getFileName().toString().contains("mdmp")))
                    .forEach(core -> gatherCoreInfo(workDir, name,
                            core, log, gathererFactory.getCoreInfoGatherer()));
        } catch (Throwable e) {
            log.printf("ERROR: exception in observer %s:", name);
            e.printStackTrace(log);
        } finally {
            log.printf("--- %s%n", name);
            if (needClose) {
                log.close();
            } else {
                log.flush();
            }
        }
    }

    private void gatherCoreInfo(Path workDir, String name, Path core, PrintWriter log,
                               CoreInfoGatherer gatherer) {
        File output = workDir.resolve(CORES_OUTPUT).toFile();
        try (HtmlPage html = new HtmlPage(new PrintWriter(
                new FileWriter(output, true), true))) {
            try (ElapsedTimePrinter timePrinter
                         = new ElapsedTimePrinter(new Stopwatch(), name, log)) {
                gatherer.gatherCoreInfo(html.getRootSection(), core);
            }
        } catch (Throwable e) {
            log.printf("ERROR: exception in observer on getting environment "
                    + "information %s:", name);
            e.printStackTrace(log);
        }
    }

    private void gatherEnvInfo(Path workDir, String name, PrintWriter log,
                               EnvironmentInfoGatherer gatherer) {
        File output = workDir.resolve(ENVIRONMENT_OUTPUT).toFile();
        try (HtmlPage html = new HtmlPage(new PrintWriter(
                new FileWriter(output, true), true))) {
            try (ElapsedTimePrinter timePrinter
                         = new ElapsedTimePrinter(new Stopwatch(), name, log)) {
                gatherer.gatherEnvironmentInfo(html.getRootSection());
            }
        } catch (Throwable e) {
            log.printf("ERROR: exception in observer on getting environment "
                    + "information %s:", name);
            e.printStackTrace(log);
        }
    }

    /*
     * The harness calls this method one time per run, not per test.
     */
    @Override
    public void startingTestRun(Parameters params) {
        RegressionParameters rp = (RegressionParameters) params;
        compileJdk = rp.getCompileJDK().getAbsoluteFile().toPath();
        testJdk = rp.getTestJDK().getAbsoluteFile().toPath();
    }
}
