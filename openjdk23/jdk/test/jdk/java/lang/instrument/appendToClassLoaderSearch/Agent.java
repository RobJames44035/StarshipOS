/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * Used by run_tests.sh
 *
 * Simple agent that makes Instrumentation instance available to other classes.
 */
import java.lang.instrument.Instrumentation;

public class Agent {

    static Instrumentation inst;

    public static void premain(String args, Instrumentation ins) {
        inst = ins;
    }

    public static Instrumentation getInstrumentation() {
        return inst;
    }

}
