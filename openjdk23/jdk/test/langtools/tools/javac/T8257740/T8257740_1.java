/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8257740
 * @summary Compiler crash when compiling type annotation on uni-catch inside lambda
 * @run compile T8257740_1.java
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class T8257740_1 {
    @Target(ElementType.TYPE_USE)
    @interface T8257740_1_Anno { }

    void test() {
        Runnable r = () -> {
            try {
                System.out.println();
            } catch (@T8257740_1_Anno Exception e) { // uni-catch
                e.printStackTrace();
            }
        };
    }
}
