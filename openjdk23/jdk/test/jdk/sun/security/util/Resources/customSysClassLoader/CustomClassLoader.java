
/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * A class loader that can be used in tests that require a custom class
 * loader. Its behavior is identical to ClassLoader.
 */

public class CustomClassLoader extends ClassLoader {
    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }
}
