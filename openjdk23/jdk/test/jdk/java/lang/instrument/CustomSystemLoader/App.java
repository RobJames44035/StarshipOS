/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.PrintStream;

public class App {

    public static void main(String args[]) throws Exception {
        (new App()).run(args, System.out);
    }

    public void run(String args[], PrintStream out) throws Exception {
        out.println("App: Test started");
        if (CustomLoader.agentClassLoader != CustomLoader.myself) {
            System.out.println("App: agentClassLoader:    " + CustomLoader.agentClassLoader);
            System.out.println("App: CustomLoader.myself: " + CustomLoader.myself);
            System.out.println("App: myClassLoader:       " + App.class.getClassLoader());
            throw new Exception("App: Agent's system class loader is not CustomLoader");
        } else if (Agent.failed) {
            throw new Exception("App: Agent failed");
        } else if (CustomLoader.failed) {
            throw new Exception("App: CustomLoader failed");
        }
        out.println("App: Test passed");
    }
}
