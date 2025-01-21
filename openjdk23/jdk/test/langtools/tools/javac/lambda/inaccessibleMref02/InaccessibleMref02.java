/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that classfiles with member ref CP entries are read correctly
 */
public class InaccessibleMref02 {
    interface SAM {
        void m();
    }

    public static void main(String[] args) {
        p1.C c = new p1.C();
        SAM s = c::m;
        s.m();
    }
}
