/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
/*
  @test
  @bug 4704042
  @summary Unit tests for Insets.set()
  @run main SetInsetsTest
*/
import java.awt.Insets;
import java.awt.EventQueue;

public class SetInsetsTest {
    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            Insets insets = new Insets(0,0,0,0);
            insets.set(100,100,100,100);
            if (insets.top != 100 ||
                insets.bottom != 100 ||
                insets.left != 100 ||
                insets.right != 100) {
                throw new RuntimeException("Test Failed!  Insets=" + insets);
            }
        });
    }
}// class SetInsetsTest
