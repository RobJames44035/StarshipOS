/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class EffectivelyFinal_neg {

    void test() {
        String s = "a";
        String s2 = "a";
        int n = 1;
        ((Runnable)
            ()-> {
                s2 = "b"; //re-assign illegal here
                System.out.println(n);
                System.out.println(s);
                s = "b"; // not effectively final
            }
        ).run();
        n = 2; // not effectively final
    }
}
