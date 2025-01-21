/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package compiler.lib.ir_framework.driver.irmatching.report;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used by {@link FailureMessageBuilder} to handle indentations in the failure message. Each indentation level
 * equals 2 whitespaces.
 */
class Indentation {
    private static final int LEVEL_SIZE = 2;
    private int indentation;

    public Indentation(int initialIndentation) {
        this.indentation = initialIndentation;
    }

    public void add() {
        indentation += LEVEL_SIZE;
    }

    public void sub() {
        indentation -= LEVEL_SIZE;
    }

    public List<String> prependForLines(List<String> lines) {
        return lines.stream()
                    .map(s -> s.replaceAll(System.lineSeparator(), System.lineSeparator() + this))
                    .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return " ".repeat(indentation);
    }
}
