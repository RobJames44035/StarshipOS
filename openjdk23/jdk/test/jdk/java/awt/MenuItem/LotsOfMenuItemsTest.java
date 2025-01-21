/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/*
 * @test
 * @bug 4175790
 * @requires os.family == "windows"
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Win32: Running out of command ids for menu items
 * @run main/manual LotsOfMenuItemsTest
 */

public class LotsOfMenuItemsTest extends ComponentAdapter {
    private static final int NUM_WINDOWS = 400;
    private static TestFrame firstFrame;

    public static void main(String[] args) throws Exception {
        LotsOfMenuItemsTest obj = new LotsOfMenuItemsTest();
        String INSTRUCTIONS = """
                This test creates lots of frames with menu bars.
                When it's done you will see two frames.
                Try to select menu items from each of them.

                If everything seems to work - test passed.
                Click "Pass" button in the test harness window.

                If test crashes on you - test failed.""";

        PassFailJFrame.builder()
                .title("LotsOfMenuItemsTest")
                .instructions(INSTRUCTIONS)
                .rows((int) INSTRUCTIONS.lines().count() + 2)
                .columns(40)
                .testUI(obj::createAndShowUI)
                .build()
                .awaitAndCheck();
    }

    private Frame createAndShowUI() {
        firstFrame = new TestFrame("First frame");
        firstFrame.addComponentListener(this);
        return firstFrame;
    }

    @Override
    public void componentShown(ComponentEvent e) {
        final int x = firstFrame.getX();
        final int y = firstFrame.getY() + firstFrame.getHeight() + 8;
        TestFrame testFrame;
        for (int i = 1; i < NUM_WINDOWS - 1; ++i) {
            testFrame = new TestFrame("Running(" + i + ")...", x, y);
            testFrame.setVisible(false);
            testFrame.dispose();
        }
        testFrame = new TestFrame("Last Frame", x, y);
        PassFailJFrame.addTestWindow(testFrame);
    }

    private static class TestFrame extends Frame {
        static int n = 0;

        public TestFrame(String title) {
            this(title, 0, 0, false);
        }

        public TestFrame(String s, int x, int y) {
            this(s, x, y, true);
        }

        private TestFrame(String title, int x, int y, boolean visible) {
            super(title);
            MenuBar mb = new MenuBar();
            for (int i = 0; i < 10; ++i) {
                Menu m = new Menu("Menu_" + (i + 1));
                for (int j = 0; j < 20; ++j) {
                    MenuItem mi = new MenuItem("Menu item " + ++n);
                    m.add(mi);
                }
                mb.add(m);
            }
            setMenuBar(mb);
            setLocation(x, y);
            setSize(450, 150);
            if (visible) {
                setVisible(true);
            }
        }
    }
}
