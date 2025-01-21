/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4825927
 * @summary generics: incorrect erasure clash reported
 * @author gafter
 *
 * @compile  BridgeClash.java
 */

interface I<T> {
    public int m(T t);
}
interface J<T> {
    public int m(T t);
}
class A implements J<String>, I<String>{
    public int m(String s){return 4321;}
}
