/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Stream;

import jdk.test.lib.process.OutputAnalyzer;

import static org.testng.Assert.*;

public class TestEnableNativeAccessBase {
    static final String MODULE_PATH = System.getProperty("jdk.module.path");

    static final String PANAMA_MAIN_CLS = "org.openjdk.foreigntest.PanamaMainDirect";
    static final String PANAMA_MAIN = "panama_module/" + PANAMA_MAIN_CLS;
    static final String PANAMA_REFLECTION_CLS = "org.openjdk.foreigntest.PanamaMainReflection";
    static final String PANAMA_REFLECTION = "panama_module/" + PANAMA_REFLECTION_CLS;
    static final String PANAMA_INVOKE_CLS = "org.openjdk.foreigntest.PanamaMainInvoke";
    static final String PANAMA_INVOKE = "panama_module/" + PANAMA_INVOKE_CLS;
    static final String PANAMA_JNI_CLS = "org.openjdk.jni.PanamaMainJNI";
    static final String PANAMA_JNI = "panama_jni_load_module/" + PANAMA_JNI_CLS;
    static final String UNNAMED = "org.openjdk.foreigntest.unnamed.PanamaMainUnnamedModule";

    /**
     * Represents the expected result of a test.
     */
    static final class Result {
        private final boolean success;
        private final List<String> expectedOutput = new ArrayList<>();
        private final List<String> notExpectedOutput = new ArrayList<>();

        Result(boolean success) {
            this.success = success;
        }

        Result expect(String msg) {
            expectedOutput.add(msg);
            return this;
        }

        Result doNotExpect(String msg) {
            notExpectedOutput.add(msg);
            return this;
        }

        boolean shouldSucceed() {
            return success;
        }

        Stream<String> expectedOutput() {
            return expectedOutput.stream();
        }

        Stream<String> notExpectedOutput() {
            return notExpectedOutput.stream();
        }

        @Override
        public String toString() {
            String s = (success) ? "success" : "failure";
            for (String msg : expectedOutput) {
                s += "/" + msg;
            }
            return s;
        }

    }

    static Result success() {
        return new Result(true);
    }

    static Result successNoWarning() {
        return success().doNotExpect("WARNING");
    }

    static Result successWithWarning(String moduleName) {
        return success().expect("WARNING").expect("--enable-native-access=" + moduleName);
    }

    static Result successWithWarnings(String... moduleNames) {
        Result result = success();
        for (String moduleName : moduleNames) {
            result = result.expect("WARNING").expect("--enable-native-access=" + moduleName);
        }
        return result;
    }

    static Result failWithWarning(String expectedOutput) {
        return new Result(false).expect(expectedOutput).expect("WARNING");
    }

    static Result failWithError(String expectedOutput) {
        return new Result(false).expect(expectedOutput);
    }

    /**
     * Checks an expected result with the output captured by the given
     * OutputAnalyzer.
     */
    void checkResult(Result expectedResult, OutputAnalyzer outputAnalyzer) {
        expectedResult.expectedOutput().forEach(outputAnalyzer::shouldContain);
        expectedResult.notExpectedOutput().forEach(outputAnalyzer::shouldNotContain);
        int exitValue = outputAnalyzer.getExitValue();
        if (expectedResult.shouldSucceed()) {
            assertTrue(exitValue == 0);
        } else {
            assertTrue(exitValue != 0);
        }
    }
}
