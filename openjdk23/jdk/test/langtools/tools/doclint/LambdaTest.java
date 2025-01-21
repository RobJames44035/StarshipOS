/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 8194069
 * @summary ignore declarations in lambda expressions
 * @modules jdk.javadoc/jdk.javadoc.internal.doclint
 * @build DocLintTester
 * @run main DocLintTester -Xmsgs:all LambdaTest.java
 */

package acme;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;

/**
 * The class has docs.
 */
public final class LambdaTest
{
    /** . */ LambdaTest() { }

    /**
     * The field itself has docs.
     */
    // Ensure no warning for lambda parameter, at 'string ->'
    static final Function<String, String> someFunction = string -> {
        // Ensure no warning for 'localVariable'
        int localVariable = 3;
        return Integer.toString(localVariable);
    };
}

