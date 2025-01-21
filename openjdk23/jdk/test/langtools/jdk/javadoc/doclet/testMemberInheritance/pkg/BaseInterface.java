/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

package pkg;

import java.lang.annotation.*;

public interface BaseInterface {

    public class NestedClassFromInterface{}

    public <A extends Annotation> A getAnnotation(Class<A> annotationClass);
}
