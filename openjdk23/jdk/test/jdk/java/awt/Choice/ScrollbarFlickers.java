/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug 6405707
 * @summary Choice popup & scrollbar gets Flickering when mouse is pressed & drag on the scrollbar
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual ScrollbarFlickers
 */

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

public class ScrollbarFlickers extends Frame {
    static final String INSTRUCTIONS = """
            Open the choice popup. Select any item in it and
            drag it with the mouse above or below the choice.
            Keep the choice opened.
            Continue dragging the mouse outside of the choice
            making content of the popup scroll.
            If you see that scrollbar flickers press Fail.
            Otherwise press Pass.
            """;

    public ScrollbarFlickers() {
        super("Scrollbar Flickering Test");
        Choice ch = new Choice();
        setLayout(new BorderLayout());
        ch.add("Praveen");
        ch.add("Mohan");
        ch.add("Rakesh");
        ch.add("Menon");
        ch.add("Girish");
        ch.add("Ramachandran");
        ch.add("Elancheran");
        ch.add("Subramanian");
        ch.add("Raju");
        ch.add("Pallath");
        ch.add("Mayank");
        ch.add("Joshi");
        ch.add("Sundar");
        ch.add("Srinivas");
        ch.add("Mandalika");
        ch.add("Suresh");
        ch.add("Chandar");
        add(ch);
        setSize(200, 200);
        validate();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame.builder()
                .title("Test Instructions")
                .testUI(ScrollbarFlickers::new)
                .instructions(INSTRUCTIONS)
                .columns(40)
                .build()
                .awaitAndCheck();
    }
}
