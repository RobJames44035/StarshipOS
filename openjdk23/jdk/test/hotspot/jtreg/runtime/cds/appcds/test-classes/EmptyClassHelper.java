/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

import java.lang.*;

import java.lang.reflect.Method;
import jdk.internal.access.JavaLangAccess;
import jdk.internal.access.SharedSecrets;

class EmptyClassHelper {
    static final JavaLangAccess jla = SharedSecrets.getJavaLangAccess();
    static final String USE_APP = "useAppLoader";
    public static void main(String[] args) throws Exception {
        Class cls = null;
        Method m = null;
        ClassLoader appLoader = ClassLoader.getSystemClassLoader();
        String className = "com.sun.tools.javac.Main";
        if (args[0].equals(USE_APP)) {
            cls = appLoader.loadClass(className);
            System.out.println("appLoader loaded class");
            try {
                m = cls.getMethod("main", String[].class);
                System.out.println("appLoader found method main");
            } catch(NoSuchMethodException ex) {
                System.out.println(ex.toString());
            }
        } else {
            cls = jla.findBootstrapClassOrNull(className);
            System.out.println("bootLoader loaded class");
            System.out.println("cls = " + cls);
            try {
                m = cls.getMethod("main", String[].class);
                System.out.println("bootLoader found method main");
            } catch(NoSuchMethodException ex) {
                System.out.println(ex.toString());
            }
        }
    }
}
