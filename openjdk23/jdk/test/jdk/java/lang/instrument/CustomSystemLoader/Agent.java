/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.PrintStream;
import java.lang.instrument.*;
import java.lang.reflect.Field;

/**
  * @test
  * @bug 8160950
  * @summary test for custom system class loader
  *
  * @run build App Agent CustomLoader
  * @run shell ../MakeJAR3.sh Agent 'Can-Retransform-Classes: true'
  * @run main/othervm -javaagent:Agent.jar -Djava.system.class.loader=CustomLoader App
  */

public class Agent {
    private static PrintStream err = System.err;
    private static PrintStream out = System.out;
    public  static boolean failed = false;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        ClassLoader myClassLoader = Agent.class.getClassLoader();
        out.println("Agent: started; myClassLoader: " + myClassLoader);
        try {
            Field fld = myClassLoader.getClass().getField("agentClassLoader");
            fld.set(myClassLoader.getClass(), myClassLoader);
        } catch (Exception ex) {
            failed = true;
            ex.printStackTrace();
        }
        out.println("Agent: finished");
   }
}
