/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class T6270087neg {

    static class Foo<X> {}

   <U extends Integer, V extends String> void test2(Foo<V> lv) {
        Object o = (Foo<U>) lv;
   }
}
