/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary symbol not found error, implicit lambdas and diamond constructor invocations
 * @compile CantFindSymbolImplicitLambdaAndDiamondTest.java
 */

import java.util.function.Consumer;

class CantFindSymbolImplicitLambdaAndDiamondTest {
    static class B<T>{}

    static class A1 {
        <T> A1(Consumer<T> cons) {}
    }

    static class A2<T> {
        A2(Consumer<T> cons) {}
    }

    public void mount() {
        new A1(inHours ->
                new B<>() {{
                    System.out.println(inHours);
                }});

        new A2<>(inHours ->
            new B<>() {{
                System.out.println(inHours);
            }});
    }
}
