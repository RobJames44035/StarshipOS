/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package sun.swing;

import java.awt.KeyEventPostProcessor;
import java.awt.Window;
import java.awt.event.KeyEvent;

import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

public final class AltProcessor implements KeyEventPostProcessor {

    private AltProcessor() {}

    private static final AltProcessor altProcessor = new AltProcessor();

    public static KeyEventPostProcessor getInstance() {
        return altProcessor;
    }

    @Override
    public boolean postProcessKeyEvent(final KeyEvent ev) {
        if (ev.getKeyCode() != KeyEvent.VK_ALT) {
            return false;
        }

        final JRootPane root = SwingUtilities.getRootPane(ev.getComponent());
        final Window winAncestor = (root == null ? null : SwingUtilities.getWindowAncestor(root));

        switch (ev.getID()) {
            case KeyEvent.KEY_PRESSED:
                MnemonicHandler.setMnemonicHidden(false);
                break;
            case KeyEvent.KEY_RELEASED:
                MnemonicHandler.setMnemonicHidden(true);
                break;
        }

        MnemonicHandler.repaintMnemonicsInWindow(winAncestor);

        return false;
    }
}
