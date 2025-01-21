/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

import java.awt.Button;
import java.awt.CheckboxMenuItem;
import java.awt.Frame;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * @test
 * @bug 6401956
 * @summary The right mark of the CheckboxMenu item is broken
 * @library /java/awt/regtesthelpers
 * @build PassFailJFrame
 * @run main/manual AppearanceIfLargeFont
 */

public class AppearanceIfLargeFont extends Frame {
    private static final String INSTRUCTIONS = """
            1) Make sure that font-size is large.
               You could change this using 'Appearance' dialog.
            2) Press button 'Press'
               You will see a menu item with check-mark.
            3) If check-mark is correctly painted then the test passed.
               Otherwise, test failed.
            """;

    public static void main(String[] args) throws Exception {
        PassFailJFrame.builder()
                .title("AppearanceIfLargeFont")
                .instructions(INSTRUCTIONS)
                .columns(35)
                .testUI(AppearanceIfLargeFont::new)
                .build()
                .awaitAndCheck();
    }

    public AppearanceIfLargeFont() {
        createComponents();

        setSize(200, 200);
        validate();
    }

    void createComponents() {
        final Button press = new Button("Press");
        final PopupMenu popup = new PopupMenu();
        press.add(popup);
        add(press);

        CheckboxMenuItem item = new CheckboxMenuItem("CheckboxMenuItem", true);
        popup.add(item);

        press.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        popup.show(press, press.getSize().width, 0);
                    }
                }
        );
    }
}
