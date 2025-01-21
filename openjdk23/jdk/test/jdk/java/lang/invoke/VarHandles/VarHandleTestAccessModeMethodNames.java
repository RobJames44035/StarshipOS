/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @run testng VarHandleTestAccessModeMethodNames
 * @modules java.base/java.lang.invoke:open
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

public class VarHandleTestAccessModeMethodNames {

    @DataProvider
    public static Object[][] accessModesProvider() {
        return Stream.of(VarHandle.AccessMode.values()).
                map(am -> new Object[]{am}).
                toArray(Object[][]::new);
    }

    @Test(dataProvider = "accessModesProvider")
    public void testMethodName(VarHandle.AccessMode am) {
        assertEquals(am.methodName(), toMethodName(am.name()));
    }

    private static String toMethodName(String name) {
        StringBuilder s = new StringBuilder(name.toLowerCase());
        int i;
        while ((i = s.indexOf("_")) !=  -1) {
            s.deleteCharAt(i);
            s.setCharAt(i, Character.toUpperCase(s.charAt(i)));
        }
        return s.toString();
    }


    @Test(dataProvider = "accessModesProvider")
    public void testReturnType(VarHandle.AccessMode am) throws Exception {
        assertEquals(getReturnType(am.methodName()), getAccessModeReturnType(am));
    }

    private static Class<?> getReturnType(String name) throws Exception {
        return VarHandle.class.getMethod(name, Object[].class).getReturnType();
    }

    private static Class<?> getAccessModeReturnType(VarHandle.AccessMode am) throws Exception {
        Field field_am_at = VarHandle.AccessMode.class.getDeclaredField("at");
        field_am_at.setAccessible(true);
        Field field_at_returnType = field_am_at.getType().getDeclaredField("returnType");
        field_at_returnType.setAccessible(true);
        return (Class<?>) field_at_returnType.get(field_am_at.get(am));
    }
}
