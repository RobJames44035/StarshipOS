/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.File;

public class RedefineOldSuperApp {
    public static void main(String args[]) throws Throwable {
        File bootJar = new File(args[0]);
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();

        Class<?> superCls = Class.forName("OldSuper", false, appClassLoader);
        System.out.println("OldSuper>> loader = " + superCls.getClassLoader());

        {
            OldSuper obj = (OldSuper)superCls.newInstance();
            System.out.println("(before transform) OldSuper>> doit() = " + obj.doit());
        }

        // Redefine the class
        byte[] bytes = Util.getClassFileFromJar(bootJar, "OldSuper");
        Util.replace(bytes, "Hello", "HELLO");
        RedefineClassHelper.redefineClass(superCls, bytes);

        {
            OldSuper obj = (OldSuper)superCls.newInstance();
            String s = obj.doit();
            System.out.println("(after transform) OldSuper>> doit() = " + s);
            if (!s.equals("HELLO")) {
                throw new RuntimeException("OldSuper doit() should be HELLO but got " + s);
            }
        }

        Class<?> childCls = Class.forName("NewChild", false, appClassLoader);
        System.out.println("NewChild>> loader = " + childCls.getClassLoader());


        {
            OldSuper obj = (OldSuper)childCls.newInstance();
            String s = obj.doit();
            System.out.println("(after transform) NewChild>> doit() = " + s);
            if (!s.equals("HELLO")) {
                throw new RuntimeException("NewChild doit() should be HELLO but got " + s);
            }
        }
    }
}
