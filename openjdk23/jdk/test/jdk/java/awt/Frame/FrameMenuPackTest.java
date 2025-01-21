/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.ScrollPane;
import java.awt.Window;
import java.util.List;

/*
 * @test
 * @bug 4084766
 * @summary Test for bug(s): 4084766
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual FrameMenuPackTest
 */

public class FrameMenuPackTest {
    private static final String INSTRUCTIONS = """
            Check that both frames that appear are properly packed with
            the scrollpane visible.
            """;

    public static void main(String[] argv) throws Exception {
        PassFailJFrame.builder()
                .title("FrameMenuPackTest Instructions")
                .instructions(INSTRUCTIONS)
                .columns(45)
                .testUI(FrameMenuPackTest::createAndShowUI)
                .positionTestUIRightRow()
                .build()
                .awaitAndCheck();
    }

    private static List<Window> createAndShowUI() {
        // Frame without menu, packs correctly
        PackedFrame f1 = new PackedFrame(false);
        f1.pack();

        // Frame with menu, doesn't pack right
        PackedFrame f2 = new PackedFrame(true);
        f2.pack();

        return List.of(f1, f2);
    }

    private static class PackedFrame extends Frame {
        public PackedFrame(boolean withMenu) {
            super("PackedFrame");

            MenuBar menubar;
            Menu fileMenu;
            MenuItem foo;
            ScrollPane sp;

            sp = new ScrollPane();
            sp.add(new Label("Label in ScrollPane"));
            System.out.println(sp.getMinimumSize());

            this.setLayout(new BorderLayout());
            this.add(sp, "Center");
            this.add(new Label("Label in Frame"), "South");

            if (withMenu) {
                menubar = new MenuBar();
                fileMenu = new Menu("File");
                foo = new MenuItem("foo");
                fileMenu.add(foo);
                menubar.add(fileMenu);
                this.setMenuBar(menubar);
            }
        }
    }
}
