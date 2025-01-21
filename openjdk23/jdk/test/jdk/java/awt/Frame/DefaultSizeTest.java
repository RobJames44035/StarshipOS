/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Frame;

/*
 * @test 4033151
 * @summary Test that frame default size is minimum possible size
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual DefaultSizeTest
 */

public class DefaultSizeTest {

    private static final String INSTRUCTIONS = """
            An empty frame is created.
            It should be located to the right of this window
            and should be the minimum size allowed by the window manager.
            For any WM, the frame should be very small.
            If the frame is not large, click Pass or Fail otherwise.
            """;


    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("DefaultSizeTest Instructions Frame")
                .instructions(INSTRUCTIONS)
                .testTimeOut(5)
                .rows(10)
                .columns(45)
                .testUI(() -> new Frame("DefaultSize"))
                .screenCapture()
                .build()
                .awaitAndCheck();
    }
}
