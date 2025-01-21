/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package foo;

public class Foo {
    /**
     * Returns all Packages visible to the class loader defining by
     * this Foo class.  Package.getPackages() is caller-sensitive.
     */
    public static Package[] getPackages() {
        return Package.getPackages();
    }
}

