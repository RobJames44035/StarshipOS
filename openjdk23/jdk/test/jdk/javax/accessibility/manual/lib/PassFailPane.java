/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package lib;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Allows to chose if a test fails or passes. It is a multi-use component. A chosen answer can be confirmed later
 * upon providing additional information.
 */
class PassFailPane extends JPanel {
    private final Consumer<Boolean> listener;

    private final JButton btnPass = new JButton("Pass");
    private final JButton btnFail = new JButton("Fail");

    /**
     * @param listener gets called with true (pass) or false (fail).
     * @throws HeadlessException
     */
    PassFailPane(Consumer<Boolean> listener)
            throws HeadlessException, IOException {
        this.listener = listener;

        add(btnPass);
        add(btnFail);

        btnPass.requestFocus();

        btnPass.addActionListener((e) -> {
            disableButtons();
            listener.accept(true);
        });

        btnFail.addActionListener((e) -> {
            disableButtons();
            listener.accept(false);
        });
    }

    private void disableButtons() {
        btnFail.setEnabled(false);
        btnPass.setEnabled(false);
    }

    public void setFailEnabled(boolean enabled) {
        btnFail.setEnabled(enabled);
    }

}
