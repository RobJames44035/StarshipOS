/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class TargetType04 {

    interface S<X extends Number, Y extends Number> {
       Y m(X x);
    }

    S<Integer, Integer> s1 = i -> { return i; }; //ok
    S<Double, Integer> s2 = i -> { return i; }; //no
    S<Integer, Double> s3 = i -> { return i; }; //no
}
