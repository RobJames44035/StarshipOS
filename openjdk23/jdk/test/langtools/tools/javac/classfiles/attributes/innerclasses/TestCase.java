/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.util.Map;
import java.util.Set;

/**
 * Class represents test cases. This class contains source code and
 * access flags for each inner class in source code.
 */
public class TestCase {

    private final String sourceCode;
    private final Map<String, Set<String>> class2Flags;

    public TestCase(String sourceCode, Map<String, Set<String>> class2Flags) {
        this.sourceCode = sourceCode;
        this.class2Flags = class2Flags;
    }

    /**
     * Returns source code.
     *
     * @return source code
     */
    public String getSource() {
        return sourceCode;
    }

    /**
     * Returns map with entries (ClassName, set of access flags for the ClassName).
     *
     * @return map with entries (ClassName, set of access flags for the ClassName)
     */
    public Map<String, Set<String>> getFlags() {
        return class2Flags;
    }
}
