/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/**
 * @test
 * @bug 8288120
 * @summary Verify an appropriate accessor method is looked up.
 */
public class ProxyMethodLookup {

    public static void main(String[] args) {
        Object val = new R(new Component());
        boolean b = val instanceof R(var c);
   }

    interface ComponentBase {}

    record Component() implements ComponentBase {}

    sealed interface Base {
        ComponentBase c();
    }

    record R(Component c) implements Base {}
}
