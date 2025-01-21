/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.lang.instrument.Instrumentation;

class SimpleAgent {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("in premain");
    }

}
