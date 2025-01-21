/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that PopupFactory.getSharedInstance() method does not throw
 *          unexpected exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessPopupFactory
 */

public class HeadlessPopupFactory {
    public static void main(String args[]) {
        PopupFactory msm = PopupFactory.getSharedInstance();
    }
}
