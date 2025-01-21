/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class DiagnosticRewriterTest2 {
   class Bar {
       Bar(Object o) { }
   }
   void test() {
      new Bar(null) {
         void g() {
            m(2L);
            m();
         }
      };
   }

   void m(int i) { }
}
