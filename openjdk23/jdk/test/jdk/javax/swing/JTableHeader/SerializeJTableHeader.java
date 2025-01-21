/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 6450193
 * @key headful
 * @summary JTableHeader doesn't uninstall UI after first serialization
 * @run main SerializeJTableHeader
 */

import javax.swing.table.JTableHeader;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.SwingUtilities;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class SerializeJTableHeader {

    private static void setLookAndFeel(UIManager.LookAndFeelInfo laf) {
        try {
            UIManager.setLookAndFeel(laf.getClassName());
        } catch (UnsupportedLookAndFeelException ignored) {
            System.out.println("Unsupported L&F: " + laf.getClassName());
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            System.out.println("Testing L&F: " + laf);
            setLookAndFeel(laf);
            JTableHeader jth = new JTableHeader();
            for (int i = 0; i < 10; i++) {
                try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(jth);
                    oos.writeObject(jth.getAccessibleContext());
                }
            }
        }
    }
}
