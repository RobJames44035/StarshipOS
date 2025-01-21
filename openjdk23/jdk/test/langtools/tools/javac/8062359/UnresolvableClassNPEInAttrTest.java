/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class UnresolvableClassNPEInAttrTest {
    public static void meth() {
        new Undefined() {
            void test() {
                new Object() {};
            }
        };
    }
}
