/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;

/*
 * @test
 * @bug 4041442
 * @key headful
 * @summary Test resizing a frame containing a canvas
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FrameResizeTest_1
 */

public class FrameResizeTest_1 {

    private static final String INSTRUCTIONS = """
        To the right of this frame is an all-white 200x200 frame.

        This is actually a white canvas component in the frame.
        The frame itself is red.
        The red should never show.
        In particular, after you resize the frame, you should see all white and no red.
        (During very fast window resizing, red color may appear briefly,
        which is not a failure.)

        Upon test completion, click Pass or Fail appropriately.
        """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("FrameResizeTest_1 Instructions")
                .instructions(INSTRUCTIONS)
                .testTimeOut(5)
                .rows(12)
                .columns(45)
                .testUI(FrameResize_1::new)
                .build()
                .awaitAndCheck();
    }
}

class FrameResize_1 extends Frame {

    FrameResize_1() {
        super("FrameResize_1");
        // Create a white canvas
        Canvas canvas = new Canvas();
        canvas.setBackground(Color.white);

        setLayout(new BorderLayout());
        add("Center", canvas);

        setBackground(Color.red);
        setSize(200,200);
    }
}
