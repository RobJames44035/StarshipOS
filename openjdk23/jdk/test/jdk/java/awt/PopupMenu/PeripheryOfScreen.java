/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.awt.Button;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 6267162
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @summary Popup Menu gets hidden below the screen when opened near the periphery
 *          of the screen, XToolkit Test if popup menu window is adjusted on screen
 *          when trying to show outside
 * @run main/manual PeripheryOfScreen
 */

public class PeripheryOfScreen {
    public static void main(String[] args) throws Exception {
        String INSTRUCTIONS = """
                Click on the button to show popup menu in the center of
                frame. Move frame beyond the edge of screen and click on
                button to show the popup menu and see if popup menu is
                adjusted to the edge.

                Press Pass if popup menu behaves as per instruction, otherwise
                press Fail.
                """;

        PassFailJFrame.builder()
                .title("PeripheryOfScreen Instruction")
                .instructions(INSTRUCTIONS)
                .columns(40)
                .testUI(PeripheryOfScreen::createUI)
                .build()
                .awaitAndCheck();
    }

    private static Frame createUI () {
        Frame f = new Frame("PeripheryOfScreen Test frame");
        Button b = new Button("Click to show popup menu");
        PopupMenu pm = new PopupMenu("Test menu");
        MenuItem i = new MenuItem("Click me");
        pm.add(i);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pm.show(f, 100, 100);
            }
        });
        f.add(b);
        f.add(pm);
        f.setSize(300, 200);
        f.toFront();
        return f;
    }
}
