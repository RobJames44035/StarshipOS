/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that redundant cast warnings are not generated for SAM conversions
 * @compile -Xlint:cast -Werror LambdaConv19.java
 */

class LambdaConv19 {

    interface SAM {
        void m();
    }

    SAM s = (SAM)()-> { };
}
