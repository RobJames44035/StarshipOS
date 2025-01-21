/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.compilercontrol.share.processors;

import compiler.compilercontrol.share.method.MethodDescriptor;
import compiler.compilercontrol.share.method.MethodGenerator;
import compiler.compilercontrol.share.pool.PoolHelper;
import compiler.compilercontrol.share.scenario.State;
import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Executable;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Log compilation file processor
 */
public class LogProcessor implements Consumer<OutputAnalyzer> {
    public static final String LOG_FILE = "compilation.log";
    private static final String TASK_ELEMENT = "<task [^>]*>";
    private static final String TASK_DONE_ELEMENT = "<task_done [^>]*>";
    private static final String TASK_END_ELEMENT = "</task>";
    private static final String ANY_ELEMENT = "<[^>]*>";
    private static final Pattern METHOD_PATTERN = Pattern.compile(
            "method='([^']+)'");
    private final List<String> loggedMethods;
    private final List<String> testMethods;

    public LogProcessor(Map<Executable, State> states) {
        loggedMethods = states.keySet().stream()
                .filter(x -> states.get(x).isLog())
                .map(MethodGenerator::commandDescriptor)
                .map(MethodDescriptor::getString)
                .collect(Collectors.toList());
        testMethods = new PoolHelper().getAllMethods()
                .stream()
                .map(pair -> pair.first)
                .map(MethodGenerator::commandDescriptor)
                .map(MethodDescriptor::getString)
                .collect(Collectors.toList());
    }

    @Override
    public void accept(OutputAnalyzer outputAnalyzer) {
        if (loggedMethods.isEmpty()) {
            return;
        }
        matchTasks();
    }

    /*
     * Gets scanner for log file of the test case
     */
    private Scanner getScanner() {
        File logFile = new File(LOG_FILE);
        Scanner scanner;
        try {
            scanner = new Scanner(logFile);
        } catch (FileNotFoundException e) {
            throw new Error("TESTBUG: file not found: " + logFile, e);
        }
        return scanner;
    }

    /*
     * Parses for &lt;task method='java.lang.String indexOf (I)I' &gt;
     * and finds if there is a compilation log for this task
     */
    private void matchTasks() {
        try (Scanner scanner = getScanner()) {
          String task = scanner.findWithinHorizon(TASK_ELEMENT, 0);
          while (task != null) {
              String element = scanner.findWithinHorizon(ANY_ELEMENT, 0);
              if (Pattern.matches(TASK_DONE_ELEMENT, element)
                      || Pattern.matches(TASK_END_ELEMENT, element)) {
                  /* If there is nothing between <task> and </task>
                     except <task done /> then compilation log is empty.
                     Check the method in this task should not be logged */
                  Asserts.assertFalse(matchMethod(task), "Compilation log "
                          + "expected. Met: " + element);
              }
              task = scanner.findWithinHorizon(TASK_ELEMENT, 0);
          }
        }
    }

    // Check that input method should be logged
    private boolean matchMethod(String input) {
        Matcher matcher = METHOD_PATTERN.matcher(input);
        Asserts.assertTrue(matcher.find(), "Wrong matcher or input");
        // Get method and normalize it
        String method = normalize(matcher.group(1));
        if (loggedMethods.contains(method)) {
            return true;
        }
        if (!testMethods.contains(method)) {
            return false;
        }
        return false;
    }

    // Normalize given signature to conform regular expression used in tests
    private String normalize(String method) {
        return method.replaceAll("\\.", "/") // replace dots in a class string
                .replaceFirst(" ", ".")      // replace space between class and method
                .replaceFirst(" ", "")       // remove space between method and signature
                .replace("&lt;", "<")
                .replace("&gt;", ">");
    }
}
