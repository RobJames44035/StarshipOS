/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class SwitchScope {
    public static void meth(String[] args) {
        switch (args.length) {
        case 0:
            final int k;
            k = 12;
            class Local {
                int j = k;
            }
        case 1:
            // the scope of a local class does not extend from one
            // switch case to the next.
            Object o = new Local();
        }
    }
}
