/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class T8154180b {
    interface Foo1 {
       Object m(String s);
    }

    interface Foo2 {
       String m(String s);
    }


    void m(Foo1 f1) { }
    void m(Foo2 f2) { }

    void test() {
        m(x->"");
        m((x->""));
        m(true ? x -> "" : x -> "");
        m((true ? x -> "" : x -> ""));
        m((true ? (x -> "") : (x -> "")));
    }
}
