/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4981727
 * @summary
 * @author Joseph D. Darcy
 */

import java.io.PrintStream;

public class exceptionCauseTest {
    public static void main(String args[]) {
        Throwable cause = new Throwable("because");
        Throwable par   = new Throwable(cause);
        TypeNotPresentException cnp = new TypeNotPresentException("test", par);

        try {
            throw cnp;
        } catch (TypeNotPresentException e) {
            if (par != e.getCause() )
                throw new RuntimeException("Unexpected value of cause.");
        }
    }
}
