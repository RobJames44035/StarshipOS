/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.TextField;

/*
 * @test
 * @bug 4097744
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary packing a frame twice stops it resizing
 * @run main/manual PackTwiceTest
 */

public class PackTwiceTest {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                1. You would see a Frame titled 'TestFrame'
                2. The Frame displays a text as below:
                    'I am a lengthy sentence...can you see me?'
                3. If you can see the full text without resizing the frame
                   using mouse, press 'Pass' else press 'Fail'.""";

        PassFailJFrame.builder()
                .title("PackTwiceTest Instruction")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(PackTwiceTest::createUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createUI() {
        Frame f = new Frame("PackTwiceTest TestFrame");
        TextField tf = new TextField();
        f.add(tf, "Center");
        tf.setText("I am a short sentence");
        f.pack();
        f.pack();
        tf.setText("I am a lengthy sentence...can you see me?");
        f.pack();
        f.requestFocus();
        return f;
    }
}
