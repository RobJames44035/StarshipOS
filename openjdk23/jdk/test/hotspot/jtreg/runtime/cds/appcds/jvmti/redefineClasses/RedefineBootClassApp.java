/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.io.File;

public class RedefineBootClassApp {
    public static void main(String args[]) throws Throwable {
        File bootJar = new File(args[0]);

        Class<?> superCls = Class.forName("BootSuper", false, null);
        System.out.println("BootSuper>> loader = " + superCls.getClassLoader());

        {
            BootSuper obj = (BootSuper)superCls.newInstance();
            System.out.println("(before transform) BootSuper>> doit() = " + obj.doit());
        }

        // Redefine the class
        byte[] bytes = Util.getClassFileFromJar(bootJar, "BootSuper");
        Util.replace(bytes, "Hello", "HELLO");
        RedefineClassHelper.redefineClass(superCls, bytes);

        {
            BootSuper obj = (BootSuper)superCls.newInstance();
            String s = obj.doit();
            System.out.println("(after transform) BootSuper>> doit() = " + s);
            if (!s.equals("HELLO")) {
                throw new RuntimeException("BootSuper doit() should be HELLO but got " + s);
            }
        }

        Class<?> childCls = Class.forName("BootChild", false, null);
        System.out.println("BootChild>> loader = " + childCls.getClassLoader());


        {
            BootSuper obj = (BootSuper)childCls.newInstance();
            String s = obj.doit();
            System.out.println("(after transform) BootChild>> doit() = " + s);
            if (!s.equals("HELLO")) {
                throw new RuntimeException("BootChild doit() should be HELLO but got " + s);
            }
        }
    }
}
