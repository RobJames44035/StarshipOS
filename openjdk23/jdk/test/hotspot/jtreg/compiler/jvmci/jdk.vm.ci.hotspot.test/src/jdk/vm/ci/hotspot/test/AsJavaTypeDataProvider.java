/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.vm.ci.hotspot.test;

import jdk.vm.ci.meta.JavaConstant;
import org.testng.annotations.DataProvider;

import static jdk.vm.ci.hotspot.test.TestHelper.CONSTANT_REFLECTION_PROVIDER;
import static jdk.vm.ci.hotspot.test.TestHelper.DUMMY_CLASS_INSTANCE;

public class AsJavaTypeDataProvider {

    @DataProvider(name = "asJavaTypeDataProvider")
    public static Object[][] asJavaTypeDataProvider() {
        return new Object[][]{
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DummyClass.class),
                                        "jdk.vm.ci.hotspot.test.DummyClass"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(boolean.class), "boolean"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(byte.class), "byte"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(short.class), "short"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(char.class), "char"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(int.class), "int"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(long.class), "long"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(float.class), "float"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(double.class), "double"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(Object.class), "java.lang.Object"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(boolean[].class), "boolean[]"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(boolean[][].class), "boolean[][]"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(Object[].class), "java.lang.Object[]"},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(Object[][].class), "java.lang.Object[][]"},
                        {JavaConstant.forBoolean(DUMMY_CLASS_INSTANCE.booleanField), null},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.objectField), null},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE), null},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.booleanArrayWithValues), null},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.booleanArrayArrayWithValues), null},
                        {JavaConstant.NULL_POINTER, null}, {null, null}};
    }
}
