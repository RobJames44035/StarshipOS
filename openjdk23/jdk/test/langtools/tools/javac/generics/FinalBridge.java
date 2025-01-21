/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @bug 4633201
 * @summary generics: bridge for inherited final method can cause verify error
 * @author gafter
 *
 * @compile  FinalBridge.java
 * @run main FinalBridge
 */

public class FinalBridge extends MyEnum<FinalBridge> {
    public static void main(String[] args) {}
}
interface MyComparable<T extends MyComparable<T>> {
    int compareTo(T other);
}
class MyEnum<E extends MyEnum<E>> implements MyComparable<E> {
    public final int compareTo(E other) {
        return 0;
    }
}
