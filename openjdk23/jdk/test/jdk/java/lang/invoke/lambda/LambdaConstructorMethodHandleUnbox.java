/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8016761
 * @summary Lambda metafactory: incorrect type conversion of constructor method handle
 */

public class LambdaConstructorMethodHandleUnbox {
    interface IntFunction<X> {
        int m(X x);
    }

    public static void main(String[] args) {
       IntFunction<String> s = Integer::new;
       if (s.m("2000") + s.m("13") != 2013) {
           throw new RuntimeException("Lambda conversion failure");
       }
    }
}
