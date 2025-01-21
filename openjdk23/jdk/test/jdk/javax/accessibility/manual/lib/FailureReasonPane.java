/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package lib;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.util.function.Consumer;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

/**
 * Allows to enter reason for the test failure.
 */
class FailureReasonPane extends JPanel {

    private final JTextArea text;

    FailureReasonPane(Consumer<String> listener) {
        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Failure reason:"), NORTH);
        text = new JTextArea(3, 10);
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                listener.accept(text.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                listener.accept(text.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                listener.accept(text.getText());
            }
        });
        add(text, CENTER);
    }

    public String getReason() {
        return text.getText();
    }

    public void requestFocus() {
        text.requestFocus();
    }
}
