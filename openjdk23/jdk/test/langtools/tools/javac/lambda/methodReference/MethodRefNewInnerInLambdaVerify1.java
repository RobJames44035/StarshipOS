/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test
 * @bug 8037404
 * @summary javac NPE or VerifyError for code with constructor reference of inner class
 */

import java.util.function.Function;
import java.util.stream.Stream;

public class MethodRefNewInnerInLambdaVerify1 {
    public static void main(String[] args) {
        if (new MethodRefNewInnerInLambdaVerify1().map().apply(1).getClass() != TT.class)
            throw new AssertionError("sanity failed");
    }

    Function<Integer,TT> map() {
        return (i) -> Stream.of(i).map(TT::new).findFirst().get();
    }

    class TT {
        public TT(int i) {

        }
    }
}
