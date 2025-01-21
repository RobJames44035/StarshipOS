/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

class DiagnosticRewriterTest {
   void test() {
      new Object() {
         void g() {
            m(2L);
         }
      };
   }

   void m(int i) { }
}
