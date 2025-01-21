/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6481655
 * @summary Parser confused by combination of parens and explicit type args
 * @author Maurizio Cimadamore
 */

public class T6481655 {

    public static <T> T getT(T t) {
        return t;
    }

    public static void main(String... s) {
        T6481655.<String>getT("");
        (T6481655.<String>getT("")).getClass();
    }
}
