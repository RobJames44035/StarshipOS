/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6367251
 * @summary 2 items are highlighted when pressing, dragging the mouse inside the choice, XToolkit
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MultiItemSelected_DragOut
 */

import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.lang.reflect.InvocationTargetException;

public class MultiItemSelected_DragOut extends Frame {
    static final String INSTRUCTIONS = """
            1) Open Choice.
            2) Start drag from first item to second or third one.
            3) Without releasing left mouse button
               press and release right mouse button.
            4) Release left mouse button.
            5) Open choice again.
            6) If there is only one selection cursor
               in the dropdown list press Pass otherwise press Fail.
            """;

    public MultiItemSelected_DragOut() {
        Choice choice = new Choice();

        for (int i = 1; i < 10; i++) {
            choice.add("item " + i);
        }
        add(choice);
        choice.addItemListener(ie -> System.out.println(ie));

        setLayout(new FlowLayout());
        setSize(200, 200);
        validate();
    }

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        PassFailJFrame.builder()
                .title("MultiItemSelected Drag Out Test")
                .testUI(MultiItemSelected_DragOut::new)
                .instructions(INSTRUCTIONS)
                .columns(40)
                .build()
                .awaitAndCheck();
    }
}
