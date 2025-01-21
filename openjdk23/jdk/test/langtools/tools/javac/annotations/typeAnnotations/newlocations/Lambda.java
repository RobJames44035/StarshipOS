/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8008077 8029721
 * @summary new type annotation location: lambda expressions
 * javac crash for annotated parameter type of lambda in a field
 * @compile Lambda.java
 * @author Werner Dietl
 */

import java.lang.annotation.*;

public class Lambda {

    interface LambdaInt {
        <S, T> void generic(S p1, T p2);
    }

    static class LambdaImpl implements LambdaInt {
        <S, T> LambdaImpl(S p1, T p2) {}
        public <S, T> void generic(S p1, T p2) {}
    }

    LambdaInt getMethodRefTA(LambdaImpl r) {
        return r::<@TA Object, @TB Object>generic;
    }

    LambdaInt getConstructorRefTA() {
        return LambdaImpl::<@TA Object, @TB Object>new;
    }

    interface LambdaInt2 {
        void lambda(Object p1, Object p2);
    }

    LambdaInt2 getLambda() {
        return (@TA Object x, @TB Object y) -> { @TA Object l = null; System.out.println("We have: " + (@TB Object) x); };
    }

    java.util.function.IntUnaryOperator x = (@TA int y) -> 1;

    static java.util.function.IntUnaryOperator xx = (@TA int y) -> 1;

    java.util.function.IntUnaryOperator foo() {
        return (@TA int y) -> 2;
    }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface TA { }

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface TB { }
