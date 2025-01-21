/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import testpackage.Main;

/**
 * This class is used in MVJarAsLibraryTest.java test.
 * It is a part of the test.
 */
public class UseByImport {

    /**
     * Method for the test execution.
     * @param args - no args needed
     */
    public static void main(String[] args) {
        System.out.println("Main version: " + Main.getMainVersion());
        System.out.println("Helpers version: " + Main.getHelperVersion());
        System.out.println("Resource version: " + Main.getResourceVersion());
    }
}
