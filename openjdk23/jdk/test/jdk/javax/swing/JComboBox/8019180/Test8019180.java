/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.util.concurrent.CountDownLatch;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @test
 * @key headful
 * @bug 8019180
 * @summary Tests that combobox works if it is used as action listener
 * @author Sergey Malenkov
 */

public class Test8019180 implements Runnable {
    private static final CountDownLatch LATCH = new CountDownLatch(1);
    private static final String[] ITEMS = {"First", "Second", "Third", "Fourth"};
    private static JFrame frame;
    private static boolean selectionFail = false;

    public static void main(String[] args) throws Exception {
        try {
            SwingUtilities.invokeLater(new Test8019180());
            LATCH.await();
            System.out.println("selectionFail " + selectionFail);
            if (selectionFail) {
                throw new RuntimeException("Combobox not selected");
            }
        } finally {
            SwingUtilities.invokeAndWait(() -> {
                if (frame != null) {
                    frame.dispose();
                }
            });
        }
    }

    private JComboBox<String> test;

    @Override
    public void run() {
        if (this.test == null) {
            this.test = new JComboBox<>(ITEMS);
            this.test.addActionListener(this.test);
            frame = new JFrame();
            frame.add(test);
            frame.setLocationRelativeTo(null);
            frame.pack();
            frame.setVisible(true);
            SwingUtilities.invokeLater(this);
        } else {
            int index = this.test.getSelectedIndex();
            this.test.setSelectedIndex(1 + index);
            if (0 > this.test.getSelectedIndex()) {
                System.err.println("ERROR: no selection");
                selectionFail = true;
            }
            LATCH.countDown();
        }
    }
}
