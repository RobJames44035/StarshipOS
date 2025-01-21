/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

import javax.swing.plaf.RootPaneUI;

/*
 * @test
 * @bug 4224113
 * @summary Tests the presence of RootPaneUI
 * @run main bug4224113
 */

public class bug4224113 {
    public static class TestRootPaneUI extends RootPaneUI {
    }

    public static void main(String[] args) {
        TestRootPaneUI r = new TestRootPaneUI();
        System.out.println("Test Passed!");
    }
}
