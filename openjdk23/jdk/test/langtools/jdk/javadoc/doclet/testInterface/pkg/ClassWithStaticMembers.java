/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package pkg;

public class ClassWithStaticMembers implements InterfaceWithStaticMembers {

    /** A hider inner class */
    public static class InnerClass{}

    /** A hider field */
    public static int f = 1;

    /** A hider method */
    public static void m(){}

    // no comment
    public static void staticMethod() {}

}
