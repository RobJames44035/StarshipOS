/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 6913768
 * @summary With default SynthLookAndFeel instance installed new JTable creation leads to throwing NPE
 * @author Pavel Porvatov
 * @run main bug6913768
 */

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;

public class bug6913768 {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new SynthLookAndFeel());

        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                JFrame frame = new JFrame();

                JTable table = new JTable(new Object[][]{{"1", "2"}, {"3", "4"}},
                        new Object[]{"col1", "col2"});

                frame.getContentPane().add(new JScrollPane(table));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(300, 200);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
