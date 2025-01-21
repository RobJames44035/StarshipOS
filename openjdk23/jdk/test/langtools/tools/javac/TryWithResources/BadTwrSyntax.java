/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.io.IOException;
public class BadTwrSyntax implements AutoCloseable {
    public static void meth() {
        // illegal double semicolon ending resources
        try(BadTwr twrflow = new BadTwr();;) {
            System.out.println(twrflow.toString());
        }

        // but one semicolon is fine
        try(BadTwr twrflow = new BadTwr();) {
            System.out.println(twrflow.toString());
        }
    }

    public void close() {
        ;
    }
}
