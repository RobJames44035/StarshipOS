/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * Used by DynamicTest - simple Application with one method that returns the
 * number of messages printed. This is the "instrumented" form.
 */
public class Application {

    public int doSomething() {
        org.tools.Tracer.trace("MethodEnter");
        System.out.println("Bring us up to warp speed!");
        org.tools.Tracer.trace("MethodExit");
        return 3;
    }
}
