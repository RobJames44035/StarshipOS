/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8304487 8325257
 * @summary Compiler Implementation for Primitive types in patterns, instanceof, and switch (Preview)
 * @build KullaTesting TestingInputStream
 * @run testng PrimitiveInstanceOfTest
 */
import jdk.jshell.JShell;
import org.testng.annotations.Test;

import java.util.function.Consumer;

import static org.testng.Assert.assertEquals;

@Test
public class PrimitiveInstanceOfTest extends KullaTesting {

    public void testInstanceOf() {
        assertEval("int i = 42;");
        assertEval("i instanceof Integer");
        assertEval("i instanceof int");
    }

    public void testInstanceOfRef() {
        assertEval("Integer i = 42;");
        assertEval("i instanceof Integer");
        assertEval("i instanceof Number");
    }

    public void testInstanceOfObjectToPrimitive() {
        assertEval("Object o = 1L;");
        assertEval("o instanceof long");
        assertEval("o instanceof Long");
    }

    public void testInstanceOfPrimitiveToPrimitiveInvokingExactnessMethod() {
        assertEval("int b = 1024;");
        assertEval("b instanceof byte");
    }

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        super.setUp(bc -> bc.compilerOptions("--source", System.getProperty("java.specification.version"), "--enable-preview").remoteVMOptions("--enable-preview"));
    }
}
