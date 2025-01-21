/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8169345
 * @summary javac crash when local from enclosing context is captured multiple times
 */

public class T8169345b {
    void test() {
        Object o = new Object();
        class Local1 {
            Object test1() {
                return o;
            }
        }
        class Local2 {
            void test2() {
                Object o = new Object();
                class Local3 {
                    Object test3() {
                        return o;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Class.forName("T8169345b$1Local1");
        Class.forName("T8169345b$1Local2$1Local3");
        Class.forName("T8169345b$1Local2");
    }
}
