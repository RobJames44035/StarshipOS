/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package pkg1;

/**
 * A class with package access, inaccessible to classes outside its package.
 */
interface ClassA {

    /**
     * A string constant with package access.
     */
    static final String STRING_A = "A string in pkg1 with package access.";

    /**
     * A method with package access.
     */
    default void methodA() {
        System.out.println(ClassA.class.getName() + ": " + STRING_A);
    }
}
