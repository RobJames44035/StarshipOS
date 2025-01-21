
/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8245153
 * @summary Unicode encoded double-quoted empty string does not compile
 * @compile T8245153.java
 */

public class T8245153 {

    String s0 = "";
    String s1 = \u0022\u0022;
    String s2 = "\u0022;
    String s3 = \u0022";
    String s4 = \u0022\\u0022\u0022;

}
