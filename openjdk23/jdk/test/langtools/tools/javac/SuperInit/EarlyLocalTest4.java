/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8333313
 * @summary Verify references to local classes declared in early construction contexts
 * @enablePreview
 */
public class EarlyLocalTest4 {

    class Test {
        Test() {
            class InnerLocal { }
            Runnable r = new Runnable() {
                public void run() {
                    new InnerLocal();
                }
            };
            r.run();
            super();
        }
    }

    public static void main(String[] args) {
        new EarlyLocalTest4().new Test();
    }
}
