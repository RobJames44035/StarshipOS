/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package gc.g1.humongousObjects;

import gc.testlibrary.Helpers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Generates non-humongous and humongous class loaders.
 * Since the generation depends on current host architecture it cannot be done as part of pre-compilation step
 */
public class ClassLoaderGenerator {
    public static void main(String[] args) throws Exception {

        if (args.length != 1) {
            throw new Error("Test Bug: Expected region size wasn't provided as command line argument");
        }

        long regionSize = Long.parseLong(args[0]) * 1024 * 1024;

        Path wrkDir = Paths.get("");
        generateClassLoader(regionSize, wrkDir);

    }

    public static void generateClassLoader(long regionSize, Path wrkDir) throws Exception {
        // Generating simple classloader
        String finalSimpleClassLoaderPrototype = TestHumongousClassLoader.GENERIC_PROTOTYPE
                .replace("${Methods}",
                        TestHumongousClassLoader.LOAD_CLASS_METHOD_PROTOTYPE
                                .replace("${ClassLoadFilter}",
                                        "fileName.equals(\"" + TestHumongousClassLoader.HUMONGOUS_CLASSLOADER_NAME
                                                + "\")"))
                .replace("${ClassHeader}", TestHumongousClassLoader.CLASS_HEADER)
                .replace("${ConstructorClause}", TestHumongousClassLoader.CONSTUCTOR_PROTOTYPE);

        Helpers.generateByTemplateAndCompile(TestHumongousClassLoader.SIMPLE_CLASSLOADER_NAME, "ClassLoader",
                finalSimpleClassLoaderPrototype, TestHumongousClassLoader.CONSTUCTOR_PROTOTYPE, regionSize / 4,
                wrkDir, TestHumongousClassLoader.SIMPLE_CLASSLOADER_NAME + "Base");


        // Preparations for generating humongous classloader

        // Generating condition for loadClass method of generated class loader
        // We want the generated class loader to load only classes from G1SampleClass enum
        // All other classes should be loaded by parent classloader
        // As result we get full loadClass method
        StringBuilder classesToLoadBuilder = new StringBuilder();
        for (G1SampleClass g1SampleClass : G1SampleClass.values()) {
            if (classesToLoadBuilder.length() != 0) {
                classesToLoadBuilder.append(" || ");
            }
            classesToLoadBuilder.append("fileName.startsWith(\"" + Helpers.enumNameToClassName(g1SampleClass.name())
                    + "\")");
        }

        // Generating final class loader prototype - with specified methods,header and constructor
        String finalHumongousClassLoaderPrototype = TestHumongousClassLoader.GENERIC_PROTOTYPE
                .replace("${Methods}",
                        TestHumongousClassLoader.LOAD_CLASS_METHOD_PROTOTYPE
                                .replace("${ClassLoadFilter}", classesToLoadBuilder))
                .replace("${ClassHeader}", TestHumongousClassLoader.CLASS_HEADER)
                .replace("${ConstructorClause}", TestHumongousClassLoader.CONSTUCTOR_PROTOTYPE);


        // Generating humongous classloader with specified name, base class, final class prototype and
        // constructor prototype for filler classes
        // Generated class instance should be humongous since we specify size of (regionSize * 3 / 4)
        Helpers.generateByTemplateAndCompile(TestHumongousClassLoader.HUMONGOUS_CLASSLOADER_NAME, "ClassLoader",
                finalHumongousClassLoaderPrototype, TestHumongousClassLoader.CONSTUCTOR_PROTOTYPE,
                regionSize * 3 / 4,
                wrkDir, TestHumongousClassLoader.HUMONGOUS_CLASSLOADER_NAME + "Base");


    }
}
