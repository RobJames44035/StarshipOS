/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import java.net.*;
import java.io.*;

public class Test {
    public static void main (String[] args) throws Exception {
        test1(args[0]);
    }

    public static void test1 (String s) throws Exception {
        URLClassLoader cl = new URLClassLoader (new URL[] {
            new URL ("file:" + s)
        });
        Class clazz = Class.forName ("Test\u00a3", true, cl);
        InputStream is = clazz.getResourceAsStream ("Test\u00a3.class");
        is.read();
        is = clazz.getResourceAsStream ("Rest\u00a3.class");
        is.read();
    }
}
