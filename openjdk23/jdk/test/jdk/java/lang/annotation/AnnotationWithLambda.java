/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8147585
 * @summary Check Annotation with Lambda, with or without parameter
 * @run testng AnnotationWithLambda
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.function.Consumer;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AnnotationWithLambda {

    @Test
    public void testAnnotationWithLambda() {
        Method[] methods = AnnotationWithLambda.MethodsWithAnnotations.class.getDeclaredMethods();
        for (Method method : methods) {
            assertTrue((method.isAnnotationPresent(LambdaWithParameter.class)) &&
                       (method.isAnnotationPresent(LambdaWithoutParameter.class)));

        }
    }

    static class MethodsWithAnnotations {

        @LambdaWithParameter
        @LambdaWithoutParameter
        public void testAnnotationLambda() {
        }
    }
}

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface LambdaWithParameter {
    Consumer<Integer> f1 = a -> {
        System.out.println("lambda has parameter");
    };
}

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface LambdaWithoutParameter {
    Runnable r = () -> System.out.println("lambda without parameter");
}

