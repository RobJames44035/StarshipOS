/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4153020
   @summary Negative marklimit value should throw an exception
   */

import java.io.*;

public class NegativeMarkLimit {

    public static void main( String argv[] ) throws Exception {

        StringReader in = new StringReader("aaaaaaaaaaaaaaa");
        try {
            in.mark(-1);
        } catch (IllegalArgumentException e){
            return;
        }
        throw new
            Exception(" Negative marklimit value should throw an exception");
    }
}
