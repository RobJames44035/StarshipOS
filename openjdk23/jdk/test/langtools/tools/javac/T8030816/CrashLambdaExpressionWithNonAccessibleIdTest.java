/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/* This test must make sure that javac won't crash when compiling lambda
 * containing an anonymous innerclass based on an unresolvable type.
 */
public class CrashLambdaExpressionWithNonAccessibleIdTest {
    void m() {
        m1(()-> {
            new A(){
                public void m11() {}
            };
        });

    }

    void m1(Runnable r) {}
}
