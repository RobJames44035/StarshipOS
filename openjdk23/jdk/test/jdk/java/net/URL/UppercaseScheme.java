/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4417734
 * @summary Test that piece-wise URL constructor should ignore case
 * of scheme.
 */
import java.net.URL;

public class UppercaseScheme {

    public static void main(String args[]) throws Exception {
        URL u = new URL("HTTP", "10:100::1234", 9999, "/index.html");
    }

}
