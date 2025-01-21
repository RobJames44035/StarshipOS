/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug 8003280
 * @summary Add lambda tests
 *  check that code generation handles void-compatibility correctly
 * @run main MethodReference34
 */

public class MethodReference34 {

    static int assertionCount = 0;

    static void assertTrue(boolean cond) {
        assertionCount++;
        if (!cond)
            throw new AssertionError();
    }

    interface SAM_void<X> {
        void m();
    }

    interface SAM_java_lang_Void<X> {
        void m();
    }

    static void m_void() { assertTrue(true); }

    static Void m_java_lang_Void() { assertTrue(true); return null; }

    public static void main(String[] args) {
        SAM_void s1 = MethodReference34::m_void;
        s1.m();
        SAM_java_lang_Void s2 = MethodReference34::m_void;
        s2.m();
        SAM_void s3 = MethodReference34::m_java_lang_Void;
        s3.m();
        SAM_java_lang_Void s4 = MethodReference34::m_java_lang_Void;
        s4.m();
        assertTrue(assertionCount == 4);
    }
}
