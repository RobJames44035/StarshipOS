/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/**
 * @test
 * @bug 7032791
 * @author Alexander Potochkin
 * @summary TableCellRenderer.getTableCellRendererComponent() doesn't accept null JTable with GTK+ L&F
 */

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.table.TableCellRenderer;

public class bug7032791 {

    public static void main(String[] args) throws Exception {

        UIManager.setLookAndFeel(new SynthLookAndFeel());

        Object value = "Test value";
        JTable table = new JTable(1, 1);
        TableCellRenderer renderer = table.getDefaultRenderer(Object.class);
        renderer.getTableCellRendererComponent(null, value, true, true, 0, 0);
        System.out.println("OK");
    }
}

