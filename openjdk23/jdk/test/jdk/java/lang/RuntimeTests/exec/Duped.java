/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/* @test
   @bug 4180429
   @summary Lossage in dup2 if System.in is closed.
   @requires vm.flagless
   @run main/othervm Duped
 */

import java.io.BufferedReader;
import java.io.PrintStream;
import java.io.InputStreamReader;
import java.io.File;

public class Duped {

    public static class Echo {
        public static void main(String args[]) throws Exception {
            StringBuffer s = new StringBuffer();
            int c;
            while ((c = System.in.read()) != -1)
                s.append((char)c);
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Exception {

        String command =
            System.getProperty("java.home") +
            File.separator +
            "bin" +
            File.separator +
            "java -classpath " +
            System.getProperty("java.class.path") +
            " Duped$Echo";

        if (args.length == 1 && args[0].equals("-dont")) {
            /*
             * To quickly check that this test is working when it is
             * supposed to, just run it with -dont and it shouldn't
             * complain at all.
             */
        } else {
            /*
             * In normal runs we just close in, and that causes
             * lossage on fork.
             */
            System.in.close();
        }

        Process p = Runtime.getRuntime().exec(command);
        PrintStream out = new PrintStream(p.getOutputStream());
        out.println(HELLO);
        out.close();

        BufferedReader in =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
        String read = in.readLine();

        if (!HELLO.equals(read)) {
            throw new Exception("Failed, read ``" + read + "''");
        }
    }

    static final String HELLO = "Hello, world!";

}
