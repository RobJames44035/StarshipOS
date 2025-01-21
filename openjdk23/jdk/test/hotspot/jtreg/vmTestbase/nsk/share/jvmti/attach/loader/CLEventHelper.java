/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
/*
 * CLEventHelper.java
 *
 * Created on June 3, 2005, 1:21 PM
 *
 */

package nsk.share.jvmti.attach.loader;
/**
 * This class Object is created to make sure that all the classload
 * and unload events are properly called.
 * Depenening on Requirement This class is Loaded / Unloaded as many times
 * as possible. This gives a simple / and same class to load and unload
 * mutiple times.
 * And the events are called acurately.
 * Insted of writing a same javac class and missings its load operation.
 */
public class CLEventHelper {

    /**
     * Creates a new instance of CLEventHelper
     */
    public CLEventHelper() {

    }
    // This static method is a dummy one. which Opens some
    // class in this package
     static {
         //EventObject object = new EventObject(" Class-Load-Helper");
     }

}
