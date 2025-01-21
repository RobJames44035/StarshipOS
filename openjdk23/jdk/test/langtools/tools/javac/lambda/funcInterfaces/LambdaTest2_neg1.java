/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class LambdaTest2_neg1 {

    public static void meth() {
        LambdaTest2_neg1 test = new LambdaTest2_neg1();
        //not convertible - QooRoo is not a SAM
        test.methodQooRoo((Integer i) -> { });
    }

    void methodQooRoo(QooRoo<Integer, Integer, Void> qooroo) { }
}
