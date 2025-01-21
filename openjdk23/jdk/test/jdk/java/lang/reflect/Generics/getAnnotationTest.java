/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4979440
 * @summary Test for signature parsing corner case
 * @author Joseph D. Darcy
 */

import java.lang.reflect.*;
import java.lang.annotation.*;

/*
 * Make sure:
 * 1. getAnnotation can be called directly
 * 2. getAnnotation can be called reflectively
 * 3. generic information methods on the Method object for
 * getAnnotation can be called
 */

public class getAnnotationTest {
    public static void main (String[] args) throws Throwable {
        // Base level
        Class c = Class.forName("java.lang.annotation.Retention");
        Annotation result  = c.getAnnotation(Retention.class);
        // System.out.println("Base result:" + result);

        // Meta level, invoke Class.getAnnotation reflectively...
        Class meta_c = c.getClass();
        Method meta_getAnnotation = meta_c.getMethod("getAnnotation",
                                                     (Retention.class).getClass());

        Object meta_result = meta_getAnnotation.invoke(c, Retention.class);
        // System.out.println("Meta result:" + meta_result);

        if (!meta_result.equals(result)) {
            throw new RuntimeException("Base and meta results are not equal.");
        }

        meta_getAnnotation.getGenericExceptionTypes();
        meta_getAnnotation.getGenericParameterTypes();
        meta_getAnnotation.getGenericReturnType();
    }
}
