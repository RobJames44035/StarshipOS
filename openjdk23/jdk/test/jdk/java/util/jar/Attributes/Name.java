/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4199981
   @summary Make sure empty string is not a valid
            Attributes name.
   */


import java.util.jar.Attributes;

public class Name {
    public static void main(String[] args) throws Exception {
        try {
            Attributes.Name name = new Attributes.Name("");
            throw new Exception("empty string should be rejected");
        } catch (IllegalArgumentException e) {
        }
    }
}
