/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T7205797<X> {

    class Inner<Y> {}

    T7205797<String>[] o1 = new T7205797<>[1]; //error
    T7205797<String>[] o2 = new T7205797<>[1][1]; //error
    T7205797<String>[] o3 = new T7205797<>[1][1][1]; //error

    T7205797<String>[] o4 = new T7205797<>[] { }; //error
    T7205797<String>[] o5 = new T7205797<>[][] { }; //error
    T7205797<String>[] o6 = new T7205797<>[][][] { }; //error

    T7205797<String>.Inner<String>[] o1 = new T7205797<String>.Inner<>[1]; //error
    T7205797<String>.Inner<String>[] o2 = new T7205797<String>.Inner<>[1][1]; //error
    T7205797<String>.Inner<String>[] o3 = new T7205797<String>.Inner<>[1][1][1]; //error

    T7205797<String>.Inner<String>[] o4 = new T7205797<String>.Inner<>[] { }; //error
    T7205797<String>.Inner<String>[] o5 = new T7205797<String>.Inner<>[][] { }; //error
    T7205797<String>.Inner<String>[] o6 = new T7205797<String>.Inner<>[][][] { }; //error
}
