/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8334121
 * @summary Anonymous class capturing two enclosing instances fails to compile
 * @enablePreview
 */

public class MultiLevelOuterInstance {

    interface A {
        void run();
    }
    interface B {
        void run();
    }

    class Inner1 {
        Inner1() {
            this(new A() {
                class Inner2 {
                    Inner2() {
                        this(new B() {
                            public void run() {
                                m();
                                g();
                            }
                        });
                    }

                    Inner2(B o) {
                        o.run();
                    }
                }

                public void run() {
                    new Inner2();
                }

                void m() { }
            });
        }

        Inner1(A o) { }
    }
    void g() { }

    public static void main(String[] args) {
        new MultiLevelOuterInstance().new Inner1();
    }
}
