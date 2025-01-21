/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4040456
 * @summary Test biginteger constructor with i18n string
 */

import java.math.*;

/**
 * This class tests to see if creating a biginteger with an
 * unicode japanese zero and one succeeds
 *
 */
public class UnicodeConstructor {

      public static void main(String args[]) {

         try {
             // the code for japanese zero
             BigInteger b1 = new BigInteger("\uff10");
             System.err.println(b1.toString());

             // Japanese 1010
             BigInteger b2 = new BigInteger("\uff11\uff10\uff11\uff10");
             System.err.println(b2.toString());
          }
          catch (ArrayIndexOutOfBoundsException e) {
              throw new RuntimeException(
                       "BigInteger is not accepting unicode initializers.");
          }

     }

}
