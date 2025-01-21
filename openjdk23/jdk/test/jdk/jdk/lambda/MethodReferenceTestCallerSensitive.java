/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.function.Function;


/**
 * @test
 * @bug 8020816
 * @author Robert Field
 */

@Test
public class MethodReferenceTestCallerSensitive {

    private static <T> void getF(T arg) {
        Function<Class<T>,Field[]> firstFunction = Class<T>::getFields;
    }

    public void testConstructorReferenceVarArgs() {
        getF("Hello World");
    }

}
