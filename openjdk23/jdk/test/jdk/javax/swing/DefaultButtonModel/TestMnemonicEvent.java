/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 6463708
 * @summary Verifies if DefaultButtonModel.setMnemonic
 *          generates ChangeEvent for no change
 * @run main TestMnemonicEvent
 */

import javax.swing.DefaultButtonModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class TestMnemonicEvent implements ChangeListener {

    private static ChangeEvent lastEvent;

    public void stateChanged(ChangeEvent e) {
        lastEvent = e;
    }

    public static void main(String[] args) {
        TestMnemonicEvent test = new TestMnemonicEvent();
        DefaultButtonModel m = new DefaultButtonModel();
        m.addChangeListener(test);
        m.setMnemonic(70);
        System.out.println("Event triggered: " + (lastEvent != null));

        // clear the recorded event, set the same mnemonic then check if another
        // event is triggered...
        lastEvent = null;
        m.setMnemonic(70);
        System.out.println("Event triggered: " + (lastEvent != null));
        if (lastEvent != null) {
            throw new RuntimeException("ChangeEvent triggered for same mnemonic");
        }
    }
}
