/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
    @test
    @summary test that ResourceBundle.getBundle can be called recursively
    @build  Test4300693RB
    @run main Test4300693
    @bug 4300693
*/

/*
 *
 */

import java.util.ResourceBundle;

public class Test4300693 {

   private static ResourceBundle rb = ResourceBundle.getBundle("Test4300693RB");

   public static void main(String[] args) {
       System.out.println(rb.getString("test result"));
   }

}
