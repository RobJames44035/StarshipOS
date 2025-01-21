/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
public class ChildOldInf implements OldInf {
    public String doit() {
        return "ChildOldInf";
    }

    public static ChildOldInf.InnerChild innerChild() {
        return new InnerChild();
    }

    public static final class InnerChild {
        public InnerChild() {
        }
        public void doTest() {
            doit(() -> {
                System.out.println("Hello from InnerChild doTest");
            });
        }
        public void doit(Runnable t) {
            t.run();
        }
    }
}
