/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8144673
 * @summary Suspect message regarding suitable enclosing instance not being in scope
 * @run main MethodRefToLocalClassMethodTest
 */


import java.util.ArrayList;
import java.util.List;

public class MethodRefToLocalClassMethodTest {

    public static void main(String[] args) {
        new MethodRefToLocalClassMethodTest().foo();
    }

    public void foo() {
        class LocalFoo {
            LocalFoo(String in) {
                if (!in.equals("Hello"))
                    throw new AssertionError("Unexpected data: " + in);
            }
        }
        List<String> ls = new ArrayList<>();
        ls.add("Hello");
        ls.stream().map(LocalFoo::new).forEach(x->{});
    }
}
