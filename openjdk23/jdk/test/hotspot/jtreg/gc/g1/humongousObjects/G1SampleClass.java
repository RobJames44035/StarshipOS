/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package gc.g1.humongousObjects;

import gc.testlibrary.Helpers;
import jdk.test.whitebox.WhiteBox;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Provides generated and compiled sample classes and theirs expected sizes.
 */
public enum G1SampleClass {

    LARGEST_NON_HUMONGOUS {
        @Override
        public long expectedInstanceSize() {
            return HALF_G1_REGION_SIZE;
        }
    },
    SMALLEST_HUMONGOUS {
        @Override
        public long expectedInstanceSize() {
            return HALF_G1_REGION_SIZE + Helpers.SIZE_OF_LONG;
        }
    },
    ONE_REGION_HUMONGOUS {
        @Override
        public long expectedInstanceSize() {
            return G1_REGION_SIZE;
        }
    },
    TWO_REGION_HUMONGOUS {
        @Override
        public long expectedInstanceSize() {
            return G1_REGION_SIZE + Helpers.SIZE_OF_LONG;
        }
    },
    MORE_THAN_TWO_REGION_HUMONGOUS {
        @Override
        public long expectedInstanceSize() {
            return G1_REGION_SIZE * 2 + Helpers.SIZE_OF_LONG;
        }
    };

    private static final long G1_REGION_SIZE = WhiteBox.getWhiteBox().g1RegionSize();
    private static final long HALF_G1_REGION_SIZE = G1_REGION_SIZE / 2;

    /**
     * Generates and compiles class with instance of specified size and loads it in specified class loader
     *
     * @param classLoader class loader which will be used to load class
     * @param wrkDir working dir where generated classes are put and compiled
     * @param classNamePrefix prefix for service classes (ones we use to create chain of inheritance)
     * @return a class with instances of the specified size loaded in specified class loader
     * @throws Exception
     */

    public Class<?> getCls(ClassLoader classLoader, Path wrkDir, String classNamePrefix)
            throws Exception {
        return Helpers.generateCompileAndLoad(classLoader, Helpers.enumNameToClassName(name()) + "Class",
                expectedInstanceSize(), wrkDir, classNamePrefix);
    }

    /**
     * @return G1SampleClass instance expected size
     */
    public abstract long expectedInstanceSize();
}
