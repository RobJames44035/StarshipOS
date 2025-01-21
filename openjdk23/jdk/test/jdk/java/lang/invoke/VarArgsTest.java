/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/* @test
 * @summary unit tests for java.lang.invoke.MethodHandles
 * @run testng/othervm -ea -esa test.java.lang.invoke.VarArgsTest
 */
package test.java.lang.invoke;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Arrays;
import java.util.List;
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
import static org.testng.AssertJUnit.*;
import org.testng.annotations.*;

public class VarArgsTest {

    @Test
    public void testWithVarargs() throws Throwable {
        MethodHandle deepToString = publicLookup()
            .findStatic(Arrays.class, "deepToString", methodType(String.class, Object[].class));
        assertFalse(deepToString.isVarargsCollector());
        MethodHandle ts = deepToString.withVarargs(false);
        assertFalse(ts.isVarargsCollector());
        MethodHandle ts1 = deepToString.withVarargs(true);
        assertTrue(ts1.isVarargsCollector());
        assertEquals("[won]", (String) ts1.invokeExact(new Object[]{"won"}));
        assertEquals("[won]", (String) ts1.invoke(new Object[]{"won"}));
        assertEquals("[won]", (String) ts1.invoke("won"));
        assertEquals("[won, won]", (String) ts1.invoke("won", "won"));
        assertEquals("[won, won]", (String) ts1.invoke(new Object[]{"won", "won"}));
        assertEquals("[[won]]", (String) ts1.invoke((Object) new Object[]{"won"}));
    }

    @Test
    public void testWithVarargs2() throws Throwable {
        MethodHandle asList = publicLookup()
            .findStatic(Arrays.class, "asList", methodType(List.class, Object[].class));
        MethodHandle asListWithVarargs = asList.withVarargs(asList.isVarargsCollector());
        assert(asListWithVarargs.isVarargsCollector());
        assertEquals("[]", asListWithVarargs.invoke().toString());
        assertEquals("[1]", asListWithVarargs.invoke(1).toString());
        assertEquals("[two, too]", asListWithVarargs.invoke("two", "too").toString());
    }

    @Test(expectedExceptions = { IllegalArgumentException.class })
    public void testWithVarargsIAE() throws Throwable {
        MethodHandle lenMH = publicLookup()
            .findVirtual(String.class, "length", methodType(int.class));
        MethodHandle lenMHWithVarargs = lenMH.withVarargs(true);
    }

}
