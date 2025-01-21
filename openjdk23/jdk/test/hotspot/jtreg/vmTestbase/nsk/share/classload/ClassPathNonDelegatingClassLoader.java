/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

package nsk.share.classload;

import java.io.*;
import java.util.*;

import nsk.share.FileUtils;

/**
 * Custom classloader that does not delegate to it's parent.
 *
 * It can load classes from classpath that have name containing
 * "Class" (any package).
 */
public class ClassPathNonDelegatingClassLoader extends ClassLoader {
        private String [] classPath;

        public ClassPathNonDelegatingClassLoader() {
                classPath = System.getProperty("java.class.path").split(File.pathSeparator);
        }

        public synchronized Class loadClass(String name, boolean resolve)
                throws ClassNotFoundException {
                Class c = findLoadedClass(name);
                if (c != null) {
                        System.out.println("Found class: " + name);
                        return c;
                }
                if (name.contains("Class")) {
                        String newName = name.replace('.', '/');
                        return loadClassFromFile(name, newName + ".class", resolve);
                } else {
                        return findSystemClass(name);
                }
        }

        private Class loadClassFromFile(String name, String fname, boolean resolve)
                throws ClassNotFoundException {
                try {
                        File target = new File("");

                        for(int i = 0; i < classPath.length; ++i) {
                                target = new File(classPath[i] + File.separator + fname);
                                if (target.exists())
                                        break;
                        }
                        if (!target.exists())
                                throw new java.io.FileNotFoundException();
                        byte[] buffer = FileUtils.readFile(target);
                        Class c = defineClass(name, buffer, 0, buffer.length);
                        if (resolve)
                                resolveClass(c);
                        return c;
                } catch (IOException e) {
                        throw new ClassNotFoundException("Exception while reading classfile data", e);
                }
        }
}
