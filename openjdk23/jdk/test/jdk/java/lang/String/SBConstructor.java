/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4915187
 * @summary Test java.lang.String constructor that takes StringBuilder
 * @key randomness
 */
import java.util.*;

public class SBConstructor {
    private static Random rnd = new Random();
    public static void main (String[] argvs) throws Exception {
        for (int i=0; i<1000; i++) {
            int length = rnd.nextInt(20) + 1;
            StringBuffer testStringBuffer = new StringBuffer();
            StringBuilder testStringBuilder = new StringBuilder();
            for(int x=0; x<length; x++) {
                char aChar = (char)rnd.nextInt();
                testStringBuffer.append(aChar);
                testStringBuilder.append(aChar);
            }
            String testString1 = new String(testStringBuffer);
            String testString2 = new String(testStringBuilder);
            if (!testString1.equals(testString2))
                throw new RuntimeException("Test failure");
        }
    }
}
