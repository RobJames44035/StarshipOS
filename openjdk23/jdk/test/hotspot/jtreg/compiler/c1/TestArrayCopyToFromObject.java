/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8160591
 * @summary C1-generated code for System.arraycopy() does not throw an ArrayStoreException if 'dst' is no a "proper" array (i.e., it is java.lang.Object)
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xcomp -XX:-UseCompressedClassPointers -XX:CompileOnly=TestArrayCopyToFromObject::test TestArrayCopyToFromObject
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:+TieredCompilation -XX:TieredStopAtLevel=1 -Xcomp -XX:+UseCompressedClassPointers -XX:CompileOnly=TestArrayCopyToFromObject::test TestArrayCopyToFromObject
 */
public class TestArrayCopyToFromObject {

    public void test(Object aArray[]) {
        Object a = new Object();

        try {
            System.arraycopy(aArray, 0, a, 0, 1);
            throw new RuntimeException ("FAILED: Expected ArrayStoreException " +
                                        "(due to destination not being an array) " +
                                        "was not thrown");
        } catch (ArrayStoreException e) {
            System.out.println("PASSED: Expected ArrayStoreException was thrown");
        }

        try {
            System.arraycopy(a, 0, aArray, 0, 1);
            throw new RuntimeException ("FAILED: Expected ArrayStoreException " +
                                        "(due to source not being an array) " +
                                        "was not thrown");
        } catch (ArrayStoreException e) {
            System.out.println("PASSED: Expected ArrayStoreException was thrown");
        }

    }

    public static void main(String args[]) {
        System.out.println("TestArrayCopyToFromObject");
        Object aArray[] = new Object[10];
        for (int i = 0; i < 10; i++) {
            aArray[i] = new Object();
        }
        new TestArrayCopyToFromObject().test(aArray);
    }
}
