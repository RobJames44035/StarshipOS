/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8334037
 * @summary Test for compiler crash when local class created in early lambda
 * @enablePreview
 */

public class LambdaLocalEarlyCrash {
    interface A { }

    class Inner {
       Inner() {
          this(() -> {
             class Local {
                void g() {
                   m();
                }
             }
             new Local().g(); // error
          });
       }

       Inner(Runnable tr) {
          tr.run();
       }
    }

    void m() {
       System.out.println("Hello");
    }

    public static void main(String[] args) {
       new LambdaLocalEarlyCrash().new Inner();
    }
}
