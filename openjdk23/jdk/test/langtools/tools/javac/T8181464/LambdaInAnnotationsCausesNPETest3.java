/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

@Anno2(value = LambdaInAnnotationsCausesNPETest3.m(x -> x))
class LambdaInAnnotationsCausesNPETest3 {
    static String m(Class<?> target) {
        return null;
    }
}
