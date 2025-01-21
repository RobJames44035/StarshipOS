/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Testing swap instruction
 * @run junit SwapTest
 */

import java.lang.classfile.ClassFile;

import static java.lang.classfile.ClassFile.ACC_PUBLIC;
import static java.lang.classfile.ClassFile.ACC_STATIC;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.lang.constant.ClassDesc;
import java.lang.constant.MethodTypeDesc;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

class SwapTest {
    @Test
    void testTryCatchCatchAll() throws Throwable {
        MethodType mt = MethodType.methodType(String.class, String.class, String.class);
        MethodTypeDesc mtd = mt.describeConstable().get();

        byte[] bytes = ClassFile.of().build(ClassDesc.of("C"), cb -> {
            cb.withMethodBody("m", mtd, ACC_PUBLIC | ACC_STATIC, xb -> {
                        xb.aload(0); // 0
                        xb.aload(1); // 1, 0
                        xb.swap();   // 0, 1
                        xb.pop();    // 1
                        xb.areturn();
                    });
        });

        MethodHandles.Lookup lookup = MethodHandles.lookup().defineHiddenClass(bytes, true);
        MethodHandle m = lookup.findStatic(lookup.lookupClass(), "m", mt);
        assertEquals(m.invoke("A", "B"), "B");
    }
}
