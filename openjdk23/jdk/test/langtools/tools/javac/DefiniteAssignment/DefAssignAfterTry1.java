/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class E1 extends Exception {}
class E2 extends Exception {}

public class DefAssignAfterTry1 {
    public static void meth() {
        boolean t = true;
        E1 se1 = new E1();
        E2 se2 = new E2();
        int i;
        try {
            if (t) {
                throw se1;
            }
        } catch (E1 e) {
            i = 0;
        }
        // the following line should result in a compile-time error
        // variable i may not have been initialized
        System.out.println(i);
        System.out.println("Error : there should be compile-time errors");
    }
}
