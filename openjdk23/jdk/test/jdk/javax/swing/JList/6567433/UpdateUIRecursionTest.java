/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @key headful
 * @bug 6567433
 *
 * @summary  JList.updateUI() for invokes updateUI() on its cellrenderer via
 * SwingUtilities.updateComponentTreeUI().
 * If the cellrenderer is a parent of this JList the method recurses
 * endless.
 * This test tests that the fix is effective in avoiding recursion.
 *
 * @run main/othervm UpdateUIRecursionTest
 */

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class UpdateUIRecursionTest extends JFrame implements ListCellRenderer {
    JList list;
    DefaultListCellRenderer renderer;

    public UpdateUIRecursionTest() {
        super("UpdateUIRecursionTest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        String[] listData = {
            "First", "Second", "Third", "Fourth", "Fifth", "Sixth"
        };

        list = new JList(listData);
        list.setCellRenderer(this);
        renderer = new DefaultListCellRenderer();
        getContentPane().add(new JScrollPane(list), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeAndWait(new Runnable() {

            @Override
            public void run() {
                UpdateUIRecursionTest obj = new UpdateUIRecursionTest();

                obj.test();

                obj.disposeUI();
            }
        });
    }

    public void test() {
        list.updateUI();
    }

    public void disposeUI() {
        setVisible(false);
        dispose();
    }

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus)
    {
        return renderer.getListCellRendererComponent(list, value, index,
                                                     isSelected, cellHasFocus);
    }
}

