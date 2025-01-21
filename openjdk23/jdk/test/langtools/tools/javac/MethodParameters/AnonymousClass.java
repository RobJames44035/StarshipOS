/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8006582
 * @summary javac should generate method parameters correctly.
 * @build MethodParametersTester ClassFileVisitor ReflectionVisitor
 * @compile -parameters AnonymousClass.java
 * @run main MethodParametersTester AnonymousClass AnonymousClass.out
 */

class AnonymousClass {

    interface I<T> {
        T m();
        T m(T x, T yx);
    }

    private class Inner implements I<String> {
        public Inner()  { }
        public Inner(String arg, String barg)  { }
        public String m() { return "0"; }
        public String m(String s, String ts) { return "0"; }
    }

    public static class Sinner implements I<Long> {
        public Sinner()  { }
        public Sinner(Long arg, Long barg)  { }
        public Long m() { return 0L; }
        public Long m(Long s, Long ts) { return s + ts; }
    }

    /** Inner class in constructor context */
    public AnonymousClass(final Long a, Long ba) {
        new I<Long>() {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
        new Inner() {
            public String m() { return null; }
            public String m(String i, String ji) { return i + ji; }
        }.m(a.toString(), ba.toString());
        new Inner(a.toString(), ba.toString()) {
            public String m() { return null; }
            public String m(String i, String ji) { return i + ji; }
        }.m(a.toString(), ba.toString());
        new Sinner() {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
        new Sinner(a, ba) {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
    }

    /** Inner class in method context */
    public void foo(final Long a, Long ba) {
        new I<Long>() {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
        new Inner() {
            public String m() { return null; }
            public String m(String i, String ji) { return i + ji; }
        }.m(a.toString(), ba.toString());
        new Inner(a.toString(), ba.toString()) {
            public String m() { return null; }
            public String m(String i, String ji) { return i + ji; }
        }.m(a.toString(), ba.toString());
        new Sinner() {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
        new Sinner(a, ba) {
            public Long m() { return null; }
            public Long m(Long i, Long ji) { return i + ji; }
        }.m(a, ba);
    }
}



