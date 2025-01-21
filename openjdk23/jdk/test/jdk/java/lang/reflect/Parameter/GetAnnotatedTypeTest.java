/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8021398
 * @compile -parameters GetAnnotatedTypeTest.java
 * @run main GetAnnotatedTypeTest
 * @summary javac should generate method parameters correctly.
 */

public class GetAnnotatedTypeTest {

    public void meth(Object param) {}

    public static void main(String[] args) throws NoSuchMethodException {
        if (GetAnnotatedTypeTest.class.getMethod("meth", Object.class).getParameters()[0].getAnnotatedType().getType() != Object.class)
            throw new RuntimeException("Parameter did not have the expected annotated type");
    }
}
