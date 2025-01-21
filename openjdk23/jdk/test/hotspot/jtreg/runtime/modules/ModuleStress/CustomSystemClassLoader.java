/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * A custom system ClassLoader to define the module "m2x" to during iterations of
 * differing test runs within the test ModuleStress.java
 */
public class CustomSystemClassLoader extends ClassLoader {
    public CustomSystemClassLoader() {
        super();
    }
    public CustomSystemClassLoader(ClassLoader parent) {
        super(parent);
    }
}
