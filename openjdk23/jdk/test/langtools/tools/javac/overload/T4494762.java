/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     4494762 6491939
 * @summary Request for Clarification of JLS 15.12.2.2
 * @author  Peter von der Ah\u00e9
 * @compile T4494762.java
 */

public class T4494762 {

    static class Homer {
        float doh(float f) {
            System.out.println("doh(float)");
            return 1.0f;
        }

        char doh(char c) {
            System.out.println("doh(string)");
            return 'd';
        }
    }

    static class Bart extends Homer {
        float doh(float f) {
            System.out.println("doh(float)");
            return 1.0f;
        }
    }

    public static void main(String[] args) {
        Bart b = new Bart();
        b.doh('x');//compiler error in this line
        b.doh(1);
        b.doh(1.0f);
    }
}
