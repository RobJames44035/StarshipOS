/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.bytecode;

import java.util.Random;

/**
 * This is template class. Loaded classes will derive from it.
 */
public class DefaultTemplateClass {

    public static void main() {
        System.out.println("In method of generated class. Random.nextDouble =  " + new Random(42).nextDouble());
        System.out.println(" Printing bytesToReplace0 bytesToReplace2");
    }

    public static long field;

    public static void methodForCompilation(Object object) {
        int i = object.hashCode();
        i = i * 2000 / 1994 + 153;
        field = i;
    }

}
