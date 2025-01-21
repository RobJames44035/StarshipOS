/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.parser.hotspot;

import compiler.lib.ir_framework.driver.irmatching.parser.TestMethods;

/**
 * This class holds the current state of the parsing of the hotspot_pid* file.
 */
class State {
    private final CompileQueueMessages compileQueueMessages;
    private final LoggedMethods loggedMethods;
    private LoggedMethod loggedMethod = LoggedMethod.DONT_CARE;

    public State(String testClassName, TestMethods testMethods) {
        this.compileQueueMessages = new CompileQueueMessages(testClassName, testMethods);
        this.loggedMethods = new LoggedMethods();
    }

    public LoggedMethods loggedMethods() {
        return loggedMethods;
    }

    public void update(String line) {
        if (compileQueueMessages.isTestMethodQueuedLine(line)) {
            processCompileQueueLine(line);
        } else if (CompilePhaseBlock.isBlockStartLine(line)) {
            processBlockStartLine(line);
        } else if (CompilePhaseBlock.isBlockEndLine(line)) {
            processBlockEndLine();
        } else {
            processNormalLine(line);
        }
    }

    private void processCompileQueueLine(String line) {
        String methodName = compileQueueMessages.parse(line);
        loggedMethods.registerMethod(methodName);
    }

    private void processBlockStartLine(String line) {
        String methodName = compileQueueMessages.findTestMethodName(line);
        if (!methodName.isEmpty()) {
            loggedMethod = loggedMethods.loggedMethod(methodName);
            if (CompilePhaseBlock.isPrintIdealStart(line)) {
                loggedMethod.beginPrintIdealBlock(line);
            } else {
                loggedMethod.beginPrintOptoAssemblyBlock();
            }
        }
    }

    private void processBlockEndLine() {
        loggedMethod.terminateBlock();
    }

    private void processNormalLine(String line) {
        loggedMethod.addLine(line);
    }
}
