/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.vm.ci.hotspot.test;

import jdk.vm.ci.meta.JavaConstant;
import org.testng.annotations.DataProvider;

import java.util.LinkedList;

import static jdk.vm.ci.hotspot.test.TestHelper.CONSTANT_REFLECTION_PROVIDER;
import static jdk.vm.ci.hotspot.test.TestHelper.DUMMY_CLASS_INSTANCE;

public class BoxPrimitiveDataProvider {

    @DataProvider(name = "boxPrimitiveDataProvider")
    public static Object[][] boxPrimitiveDataProvider() {
        LinkedList<Object[]> cfgSet = new LinkedList<>();
        // Boolean testing
        cfgSet.add(
                        new Object[]{JavaConstant.forBoolean(true), CONSTANT_REFLECTION_PROVIDER.forObject(true)});
        cfgSet.add(new Object[]{JavaConstant.forBoolean(false),
                        CONSTANT_REFLECTION_PROVIDER.forObject(false)});
        // Boxed boolean testing (returns null)
        cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(true), null});
        cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(false), null});
        for (byte number : new byte[]{-128, 0, 1, 127}) {
            // Integer primitives testing
            cfgSet.add(new Object[]{JavaConstant.forByte(number),
                            CONSTANT_REFLECTION_PROVIDER.forObject(Byte.valueOf(number))});
            cfgSet.add(new Object[]{JavaConstant.forShort(number),
                            CONSTANT_REFLECTION_PROVIDER.forObject(Short.valueOf(number))});
            cfgSet.add(new Object[]{JavaConstant.forInt(number),
                            CONSTANT_REFLECTION_PROVIDER.forObject(Integer.valueOf(number))});
            cfgSet.add(new Object[]{JavaConstant.forLong(number),
                            CONSTANT_REFLECTION_PROVIDER.forObject(Long.valueOf(number))});
            if (number >= 0) {
                cfgSet.add(new Object[]{JavaConstant.forChar((char) number),
                                CONSTANT_REFLECTION_PROVIDER.forObject(Character.valueOf((char) number))});
            }
            // Float and Double variables are not cached,
            // so the tested method returns "null" on them
            cfgSet.add(new Object[]{JavaConstant.forFloat(number), null});
            cfgSet.add(new Object[]{JavaConstant.forDouble(number), null});
            // Boxed primitives testing (return null)
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Byte.valueOf(number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Short.valueOf(number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Integer.valueOf(number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Long.valueOf(number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Character.valueOf((char) number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Float.valueOf(number)), null});
            cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(Double.valueOf(number)), null});
        }
        // Integer primitives testing with big non-cached values (returns null)
        cfgSet.add(new Object[]{JavaConstant.forShort(Short.MAX_VALUE), null});
        cfgSet.add(new Object[]{JavaConstant.forInt(Integer.MAX_VALUE), null});
        cfgSet.add(new Object[]{JavaConstant.forLong(Long.MAX_VALUE), null});
        cfgSet.add(new Object[]{JavaConstant.forChar(Character.MAX_VALUE), null});
        // Non-primitives testing
        cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.objectField), null});
        cfgSet.add(new Object[]{CONSTANT_REFLECTION_PROVIDER.forObject(DUMMY_CLASS_INSTANCE.booleanArrayWithValues),
                        null});
        // Null testing
        cfgSet.add(new Object[]{JavaConstant.NULL_POINTER, null});
        cfgSet.add(new Object[]{null, null});
        return cfgSet.toArray(new Object[0][0]);
    }
}
