/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.vm.ci.hotspot.test;

import jdk.vm.ci.meta.JavaConstant;
import org.testng.annotations.DataProvider;

import static jdk.vm.ci.hotspot.test.TestHelper.CONSTANT_REFLECTION_PROVIDER;
import static jdk.vm.ci.hotspot.test.TestHelper.DUMMY_CLASS_INSTANCE;

public class IsEmbeddableDataProvider {
    @DataProvider(name = "isEmbeddableDataProvider")
    public static Object[][] isEmbeddableDataProvider() {
        return new Object[][]{{JavaConstant.forBoolean(DUMMY_CLASS_INSTANCE.booleanField), true},
                        {JavaConstant.forByte(DUMMY_CLASS_INSTANCE.byteField), true},
                        {JavaConstant.forShort(DUMMY_CLASS_INSTANCE.shortField), true},
                        {JavaConstant.forInt(DUMMY_CLASS_INSTANCE.intField), true},
                        {JavaConstant.forLong(DUMMY_CLASS_INSTANCE.longField), true},
                        {JavaConstant.forChar(DUMMY_CLASS_INSTANCE.charField), true},
                        {JavaConstant.forFloat(DUMMY_CLASS_INSTANCE.floatField), true},
                        {JavaConstant.forDouble(DUMMY_CLASS_INSTANCE.doubleField), true},
                        {CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.objectField), true},
                        {JavaConstant.NULL_POINTER, true}, {null, true}};
    }
}
