/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import javax.swing.*;

/*
 * @test
 * @summary Check that SpinnerNumberModel constructor and methods do not throw unexpected exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessSpinnerNumberModel
 */

public class HeadlessSpinnerNumberModel {
    public static void main(String args[]) {
        SpinnerNumberModel model = new SpinnerNumberModel();
        model.setValue(5);
        model.getValue();
        model.getPreviousValue();
        model.getNextValue();
    }
}
