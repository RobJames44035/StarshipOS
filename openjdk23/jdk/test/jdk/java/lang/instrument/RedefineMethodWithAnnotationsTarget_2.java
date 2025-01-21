/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * This is the second version of this class. The first version is in
 * RedefineMethodWithAnnotationsTarget.java.
 * <p>
 * It has the same methods but with different annotations and order.
 */
public class RedefineMethodWithAnnotationsTarget {
    public void annotatedMethod(@ParameterAnnotation(
            value = ParameterAnnotation.INT_VALUE_2) int parameter) {
        System.out.println("Second version of annotatedMethod(int)");
        System.out.println("parameter is " + parameter);
     }
    public void annotatedMethod(@ParameterAnnotation(
            value = ParameterAnnotation.STRING_VALUE_2) String parameter) {
        System.out.println("Second version of annotatedMethod(String)");
        System.out.println("parameter is " + parameter);
    }
}
