/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class BadSwitchExpressionLambda {

    interface SAM {
        void invoke();
    }

    public static void m() {}
    public static void r(SAM sam) {}

    void test(int i) {
        SAM sam1 = () -> m(); //ok
        SAM sam2 = () -> switch (i) { case 0 -> m(); default -> m(); }; //not ok
        r(() -> m()); //ok
        r(() -> switch (i) { case 0 -> m(); default -> m(); }); //not ok
        return switch (i) { case 0 -> m(); default -> m(); }; //not ok
    }
}
