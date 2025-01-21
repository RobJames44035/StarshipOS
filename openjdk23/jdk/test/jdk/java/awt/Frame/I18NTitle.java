/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Window;

/*
 * @test
 * @bug 6269884 4929291
 * @summary Tests that title which contains mix of non-English characters is displayed correctly
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual I18NTitle
 */

public class I18NTitle {
    private static final String INSTRUCTIONS = """
            You will see a frame with some title (S. Chinese, Cyrillic and German).
            Please check if non-English characters are visible and compare
            the visible title with the same string shown in the label
            (it should not look worse than the label).
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("I18NTitle Instructions")
                .instructions(INSTRUCTIONS)
                .columns(50)
                .testUI(I18NTitle::createAndShowGUI)
                .build()
                .awaitAndCheck();
    }

    private static Window createAndShowGUI() {
        String s = "\u4e2d\u6587\u6d4b\u8bd5 \u0420\u0443\u0441\u0441\u043a\u0438\u0439 Zur\u00FCck";
        Frame frame = new Frame(s);
        frame.setLayout(new BorderLayout());
        Label l = new Label(s);
        frame.add(l);
        frame.setSize(400, 100);
        return frame;
    }
}
