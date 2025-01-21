/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.lang.reflect.Method;

/* @test
   @bug 7010561
   @summary Tab text position with Synth based LaF is different to Java 5/6
   @modules java.desktop/javax.swing.plaf.basic:open
   @author Pavel Porvatov
*/
public class bug7010561 {
    private static int[] TAB_PLACEMENT = {
            SwingConstants.BOTTOM,
            SwingConstants.BOTTOM,
            SwingConstants.TOP,
            SwingConstants.TOP,

    };

    private static boolean[] IS_SELECTED = {
            false,
            true,
            false,
            true
    };

    private static int[] RETURN_VALUE = {
            -1,
            1,
            1,
            -1
    };

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new SynthLookAndFeel());

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JTabbedPane tabbedPane = new JTabbedPane();

                tabbedPane.addTab("Tab 1", new JLabel("Tab 1"));

                // Ensure internal TabbedPane fields are initialized
                tabbedPane.doLayout();

                BasicTabbedPaneUI basicTabbedPaneUI = (BasicTabbedPaneUI) tabbedPane.getUI();

                try {
                    Method method = BasicTabbedPaneUI.class.getDeclaredMethod("getTabLabelShiftY", int.class,
                            int.class, boolean.class);

                    method.setAccessible(true);

                    for (int i = 0; i < 4; i++) {
                        int res = ((Integer) method.invoke(basicTabbedPaneUI, TAB_PLACEMENT[i], 0,
                                IS_SELECTED[i])).intValue();

                        if (res != RETURN_VALUE[i]) {
                            throw new RuntimeException("Test bug7010561 failed on index " + i);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Test bug7010561 passed");
            }
        });
    }
}
