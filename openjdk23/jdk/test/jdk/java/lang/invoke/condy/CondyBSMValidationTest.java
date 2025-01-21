/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8186046
 * @summary Test invalid name in name and type
 * @library /java/lang/invoke/common
 * @build test.java.lang.invoke.lib.InstructionHelper
 * @run testng CondyBSMValidationTest
 * @run testng/othervm -XX:+UnlockDiagnosticVMOptions -XX:UseBootstrapCallInfo=3 CondyBSMValidationTest
 */

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.java.lang.invoke.lib.InstructionHelper;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

import static java.lang.invoke.MethodType.methodType;

public class CondyBSMValidationTest {
    static final MethodHandles.Lookup L = MethodHandles.lookup();
    static final String BSM_TYPE = methodType(Object.class, MethodHandles.Lookup.class, String.class, Object.class)
            .toMethodDescriptorString();

    @DataProvider
    public Object[][] invalidSignaturesProvider() throws Exception {
        return Stream.of(BSM_TYPE.replace("(", ""),
                        BSM_TYPE.replace(")", ""),
                        BSM_TYPE.replace("(", "").replace(")", ""),
                        BSM_TYPE.replace(";)", ")"),
                        BSM_TYPE.replace(";", ""))
                .map(e -> new Object[]{e}).toArray(Object[][]::new);
    }

    @Test(dataProvider = "invalidSignaturesProvider")
    public void testInvalidBSMSignature(String sig) throws Exception {
        try {
            MethodHandle mh = InstructionHelper.ldcDynamicConstant(
                    L, "name", "Ljava/lang/Object;",
                    "bsm", sig
            );
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(e.getMessage().contains("Bad method descriptor"));
        }
    }
}
