/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package compiler.jvmci.common.testcases;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A test case for tests in compiler.jvmci.compilerToVM package.
 */
public class TestCase {
    private static final Class<?>[] CLASSES = {
            AbstractClass.class,
            AbstractClassExtender.class,
            AnotherSingleImplementer.class,
            AnotherSingleImplementerInterface.class,
            DoNotExtendClass.class,
            DoNotImplementInterface.class,
            MultipleAbstractImplementer.class,
            MultipleImplementer1.class,
            MultipleImplementer2.class,
            MultipleImplementersInterface.class,
            MultipleImplementersInterfaceExtender.class,
            MultiSubclassedClass.class,
            MultiSubclassedClassSubclass1.class,
            MultiSubclassedClassSubclass2.class,
            PackagePrivateClass.class,
            SimpleClass.class,
            SingleImplementer.class,
            SingleImplementerInterface.class,
            SingleSubclass.class,
            SingleSubclassedClass.class
    };

    public static Collection<Class<?>> getAllClasses() {
        return  Arrays.asList(CLASSES);
    }

    public static Collection<Executable> getAllExecutables() {
        Set<Executable> result = new HashSet<>();
        for (Class<?> aClass : CLASSES) {
            result.addAll(Arrays.asList(aClass.getMethods()));
            result.addAll(Arrays.asList(aClass.getConstructors()));
        }
        return result;
    }
}
