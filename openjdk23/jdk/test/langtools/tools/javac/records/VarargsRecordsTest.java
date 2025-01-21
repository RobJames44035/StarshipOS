/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * @test
 * @bug 8246774
 * @summary test for varargs record components
 * @run testng VarargsRecordsTest
 */
@Test
public class VarargsRecordsTest {
    public record RI(int... xs) { }
    public record RII(int x, int... xs) { }
    public record RX(int[] xs) { }

    RI r1 = new RI();
    RI r2 = new RI(1);
    RI r3 = new RI(1, 2);
    RII r4 = new RII(1);
    RII r5 = new RII(1, 2);
    RII r6 = new RII(1, 2, 3);

    public void assertVarargsInstances() {
        assertEquals(r1.xs.length, 0);
        assertEquals(r2.xs.length, 1);
        assertEquals(r3.xs.length, 2);
        assertEquals(r4.xs.length, 0);
        assertEquals(r5.xs.length, 1);
        assertEquals(r6.xs.length, 2);

        assertEquals(r2.xs[0], 1);
        assertEquals(r3.xs[0], 1);
        assertEquals(r3.xs[1], 2);

        assertEquals(r5.xs[0], 2);
        assertEquals(r6.xs[0], 2);
        assertEquals(r6.xs[1], 3);
    }

    public void testMembers() throws ReflectiveOperationException {
        Constructor c = RI.class.getConstructor(int[].class);
        assertNotNull(c);
        assertTrue(c.isVarArgs());
        Parameter[] parameters = c.getParameters();
        assertEquals(parameters.length, 1);
        assertEquals(parameters[0].getName(), "xs");

        RI ri = (RI) c.newInstance(new int[]{1, 2});
        assertEquals(ri.xs()[0], 1);
        assertEquals(ri.xs()[1], 2);

        Field xsField = RI.class.getDeclaredField("xs");
        assertEquals(xsField.getType(), int[].class);
        assertEquals((xsField.getModifiers() & Modifier.STATIC), 0);
        assertTrue((xsField.getModifiers() & Modifier.PRIVATE) != 0);
        assertTrue((xsField.getModifiers() & Modifier.FINAL) != 0);
        assertEquals(((int[]) xsField.get(ri))[0], 1);

        Method xsMethod = RI.class.getDeclaredMethod("xs");
        assertEquals(xsMethod.getReturnType(), int[].class);
        assertEquals(xsMethod.getParameterCount(), 0);
        assertEquals((xsMethod.getModifiers() & (Modifier.PRIVATE | Modifier.PROTECTED | Modifier.STATIC | Modifier.ABSTRACT)), 0);
        assertEquals(((int[]) xsMethod.invoke(ri))[0], 1);
    }

    public void testNotVarargs() throws ReflectiveOperationException {
        Constructor c = RX.class.getConstructor(int[].class);
        assertFalse(c.isVarArgs());
    }
}
