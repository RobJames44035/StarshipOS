/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package gc.g1.unloading.keepref;

import java.lang.reflect.*;
import java.util.*;

/**
 * This holder keeps class from being collected by saving link in static field of class loaded by null classloader.
 * It uses pool of classes that should reside in bootclasspath.
 */
public class NullClassloaderHolder implements RefHolder {

    private static final int NUMBER_OF_CLASSES = 1000;
    private static Set<Class<?>> classesPool = Collections.synchronizedSet(new HashSet<Class<?>>());
    private final Random random;

    static {
        for (int i = 1; i <= NUMBER_OF_CLASSES; i++) {
            String className = "gc.g1.unloading.rootSetHelper.classesPool.Class" + i;
            try {
                Class<?> clazz = Class.forName(className);
                if (clazz.getClassLoader() != null) {
                    throw new RuntimeException("Test bug! Classes from pool implied to be loaded by bootclassloader.");
                }
                classesPool.add(clazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Test bug", e);
            }
        }
    }

    public NullClassloaderHolder(long seed) {
        random = new Random(seed);
    }

    @Override
    public Object hold(Object object) {
        if (classesPool.isEmpty()) {
            return null;
        } else {
            Class<?> clazz = (Class<?>) classesPool.iterator().next();
            classesPool.remove(clazz);
            Field f = getRandomField(clazz);
            try {
                f.set(null, object);
                return clazz.newInstance();
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException e) {
                throw new RuntimeException("Test bug", e);
            }
        }
    }

    private Field getRandomField(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Field f : clazz.getFields()) {
            if (f.getName().startsWith("staticField")) {
                fields.add(f);
            }
        }
        return fields.get(random.nextInt(fields.size()));
    }

}
