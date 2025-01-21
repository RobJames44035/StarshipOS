/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.check;

import jdk.test.whitebox.WhiteBox;

/**
 * This assertion checks that class is alive using WhiteBox isClassAlive method.
 */
public class ClassAssertion extends Assertion {

    private String className;

    private boolean shouldBeAlive;

    private static long counterOfCheckedUnloaded = 0;

    private static long counterOfCheckedAlive = 0;

    public static long getCounterOfCheckedUnloaded() {
        return counterOfCheckedUnloaded;
    }

    public static long getCounterOfCheckedAlive() {
        return counterOfCheckedAlive;
    }

    public ClassAssertion(String className, boolean shouldBeAlive) {
        this.shouldBeAlive = shouldBeAlive;
        this.className = className;
    }

    @Override
    public void check() {
        boolean isAlive = WhiteBox.getWhiteBox().isClassAlive(className);
        if (isAlive != shouldBeAlive) {
            if (isAlive) {
                throw new RuntimeException("Class " + className + " was not unloaded! Failing test.");
            } else {
                throw new RuntimeException("Class " + className + " must live! Failing test.");
            }
        } else {
            System.out.println(" Check OK, class " + className + ", isAlive = " + isAlive + ", shouldBeAlive = " + shouldBeAlive);
            if (isAlive) {
                counterOfCheckedAlive++;
            } else {
                counterOfCheckedUnloaded++;
            }
        }
    }

    private static long numberOfChecksLimit = -1;

    static {
        String s;
        if ((s = System.getProperty("NumberOfChecksLimit")) != null) {
            numberOfChecksLimit = Long.valueOf(s);
        }
    }

}
