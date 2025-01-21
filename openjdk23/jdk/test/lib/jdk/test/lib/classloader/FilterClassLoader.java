/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package jdk.test.lib.classloader;

import java.util.function.Predicate;
/**
 * A classloader, which using target classloader in case provided condition
 * for class name is met, and using parent otherwise
 */
public class FilterClassLoader extends ClassLoader {

    private final ClassLoader target;
    private final Predicate<String> condition;

    public FilterClassLoader(ClassLoader target, ClassLoader parent,
            Predicate<String> condition) {
        super(parent);
        this.condition = condition;
        this.target = target;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (condition.test(name)) {
            return target.loadClass(name);
        }
        return super.loadClass(name);
    }
}
