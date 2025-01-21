/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
/*
 * @test
 * @bug 8333313
 * @summary Verify references to local classes declared in early construction contexts
 * @enablePreview
 */
import java.util.concurrent.atomic.AtomicReference;

public class EarlyLocalTest8 {

    class Test extends AtomicReference<Runnable> {
        Test(int x) {
            super(
                switch (x) {
                    case 0 -> null;
                    default -> {
                        class InnerLocal { }
                        yield () -> new InnerLocal() { { System.out.println(EarlyLocalTest8.this); } };
                    }
                });
        }
    }

    public static void main(String[] args) {
        new EarlyLocalTest8().new Test(42);
    }
}
