/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/* @test
   @bug 4086919
   @summary Correct handling of unicode escapes for line termination
   @compile UnicodeAtEOL.java
*/
public class UnicodeAtEOL {
   public static void main(String[] args) {
       // \u000D
       // should end the line; bug doesn't see it as escape
       int a; \u000D

       // \u000A
       // should end the line; bug doesn't see it as escape
       int b; \u000A

   }
}
