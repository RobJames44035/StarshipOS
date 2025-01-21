/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.io.*;
import jdk.test.lib.compiler.InMemoryJavaCompiler;
import jdk.test.lib.classloader.ClassUnloadCommon;

public class MyDiffClassLoader extends ClassLoader {

    public String loaderName;
    public static boolean switchClassData = false;

    MyDiffClassLoader(String name) {
        this.loaderName = name;
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if (!name.contains("c1r") &&
            !name.contains("c1c") &&
            !name.contains("c1s") &&
            !name.equals("p2.c2")) {
                return super.loadClass(name);
        }

        // new loader loads p2.c2
        if  (name.equals("p2.c2") && !loaderName.equals("C2Loader")) {
            Class<?> c = new MyDiffClassLoader("C2Loader").loadClass(name);
            switchClassData = true;
            return c;
        }

        byte[] data = switchClassData ? getNewClassData(name) : ClassUnloadCommon.getClassData(name);
        System.out.println("name is " + name);
        return defineClass(name, data, 0, data.length);
    }

    // Return p2.c2 with everything removed
    byte[] getNewClassData(String name) {
        return InMemoryJavaCompiler.compile("p2.c2", "package p2; public class c2 { }");
    }
}
