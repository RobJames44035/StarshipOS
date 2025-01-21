/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public class PatternVariablesAreNonFinal {
    public static void meth() {
        Object o = 32;
        if (o instanceof String s) {
            s = "hello again";
            new Runnable() {
                @Override
                public void run() {
                    System.err.println(s);
                }
            };
        }
        System.out.println("test complete");
    }
}
