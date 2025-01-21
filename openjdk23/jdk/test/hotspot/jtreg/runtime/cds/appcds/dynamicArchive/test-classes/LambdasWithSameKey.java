/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * This class is for generating lambda proxy classes with the same invoke dynamic
 * info such as: caller class, invoked name, invoked type, method type, etc.
 *
 */

public class LambdasWithSameKey {
    public static void main(String args[]) {
        boolean isRun = (args.length == 1 && args[0].equals("run")) ? true : false;
        {Runnable run1 = LambdasWithSameKey::myrun; run1.run();}
        {Runnable run1 = LambdasWithSameKey::myrun; run1.run();}
        if (isRun) {
            {Runnable run1 = LambdasWithSameKey::myrun; run1.run();}
        }
    }

    static void myrun() {
        System.out.println("myrun");
    }
}
