/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 * @test
 * @bug 4017777
 * @summary Test StringTokenizer on \f character
 */

import java.util.StringTokenizer;

/**
 * This class tests to see if the StringTokenizer recognizes
 * backslash f as a whitespace character (it should)
 */
public class FormFeed {

   public static void main(String[] argv) {
      StringTokenizer st = new StringTokenizer("ABCD\tEFG\fHIJKLM PQR");

      if (st.countTokens() != 4)
         throw new RuntimeException("StringTokenizer does not treat form feed as whitespace.");

    }
}
