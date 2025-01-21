/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8231827
 * @summary Basic pattern test
 * @compile PatternTypeTest2.java
 * @run main PatternTypeTest2
 */
public class PatternTypeTest2 {

    public static void main(String[] args) {

        Integer i = 42;
        String s = "Hello";
        Object o = i;

        if (o instanceof Integer j) {
            System.out.println("It's an Integer");
        } else {
            throw new AssertionError("Broken");
        }
    }
}
