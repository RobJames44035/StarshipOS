/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
public abstract class SuperMethodCallBroken extends Undef implements I, java.util.List<String> {
    public void test() {
        I.super.test();
    }
}
interface I {
    public default void test() {}
}
