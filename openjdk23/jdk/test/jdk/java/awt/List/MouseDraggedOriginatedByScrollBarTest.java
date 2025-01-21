/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6240151
 * @summary XToolkit: Dragging the List scrollbar initiates DnD
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual MouseDraggedOriginatedByScrollBarTest
*/

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.List;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseDraggedOriginatedByScrollBarTest {

    private static final String INSTRUCTIONS = """
            1) Click and drag the scrollbar of the list.
            2) Keep dragging till the mouse pointer goes out the scrollbar.
            3) The test failed if you see messages about events. The test passed if you don't.""";

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("MouseDraggedOriginatedByScrollBarTest Instructions")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(35)
                .testUI(MouseDraggedOriginatedByScrollBarTest::createTestUI)
                .logArea()
                .build()
                .awaitAndCheck();
    }

    private static Frame createTestUI() {
        Frame frame = new Frame();
        List list = new List(4, false);

        list.add("000");
        list.add("111");
        list.add("222");
        list.add("333");
        list.add("444");
        list.add("555");
        list.add("666");
        list.add("777");
        list.add("888");
        list.add("999");

        frame.add(list);

        list.addMouseMotionListener(
            new MouseMotionAdapter(){
                @Override
                public void mouseDragged(MouseEvent me){
                    PassFailJFrame.log(me.toString());
                }
            });

        list.addMouseListener(
            new MouseAdapter() {
                public void mousePressed(MouseEvent me) {
                    PassFailJFrame.log(me.toString());
                }

                public void mouseReleased(MouseEvent me) {
                    PassFailJFrame.log(me.toString());
                }

                public void mouseClicked(MouseEvent me){
                    PassFailJFrame.log(me.toString());
                }
            });

        frame.setLayout(new FlowLayout());
        frame.pack();
        return frame;
    }
}
