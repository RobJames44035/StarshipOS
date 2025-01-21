/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug     8202473
 * @summary Annotations on type variables with multiple bounds should be placed on their respective bound
 * @compile  TypeVariableBoundParameterIndex.java
 * @run main TypeVariableBoundParameterIndex
 */

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.TypeVariable;
import java.util.concurrent.Callable;

/*
 * A class might have multiple bounds as parameterized types with type annotations on these bounds.
 * This test assures that these bound annotations are resolved correctly.
 */
public class TypeVariableBoundParameterIndex {

    public static void main(String[] args) throws Exception {
        TypeVariable<?>[] variables = Sample.class.getTypeParameters();

        for (int i = 0; i < 2; i++) {
            TypeVariable<?> variable = variables[i];
            AnnotatedType[] bounds = variable.getAnnotatedBounds();
            AnnotatedType bound = bounds[0];
            AnnotatedParameterizedType parameterizedType = (AnnotatedParameterizedType) bound;
            AnnotatedType[] actualTypeArguments = parameterizedType.getAnnotatedActualTypeArguments();
            Annotation[] annotations = actualTypeArguments[0].getAnnotations();
            if (annotations.length != 1 || annotations[0].annotationType() != TypeAnnotation.class) {
                throw new AssertionError();
            }
        }

        TypeVariable<?> variable = variables[2];
        AnnotatedType[] bounds = variable.getAnnotatedBounds();
        AnnotatedType bound = bounds[0];
        AnnotatedParameterizedType parameterizedType = (AnnotatedParameterizedType) bound;
        AnnotatedType[] actualTypeArguments = parameterizedType.getAnnotatedActualTypeArguments();
        Annotation[] annotations = actualTypeArguments[0].getAnnotations();
        if (annotations.length != 0) {
            throw new AssertionError();
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE_USE)
    @interface TypeAnnotation { }

    static class Sample<T extends Callable<@TypeAnnotation ?>, S extends Callable<@TypeAnnotation ?>, U extends Callable<?>> { }
}
