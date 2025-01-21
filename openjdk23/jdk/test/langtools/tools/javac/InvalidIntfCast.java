/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4023359 4948130
 * @summary The compiler should detect an invalid cast between interfaces.
 * @author turnidge
 *
 * @compile InvalidIntfCast.java
 */

interface I {
    void method();
}

interface J {
    int method();
}

public class InvalidIntfCast {
    public static void main(String[] args) {
        I i = null;
        J j = (J) i;
    }
}
