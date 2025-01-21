/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.awt.Dialog;
import java.awt.Frame;
import java.util.concurrent.CountDownLatch;

/**
 * @test
 * @key headful
 * @bug 8215200
 * @summary tests mixing of modal and non-modal dialogs
 */
public final class MixOfModalAndNonModalDialogs {

    public static void main(final String[] args) throws Exception {
        final Frame frame = new Frame();
        try {
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // mix of modal, non-modal and invisible dialogs, all combinations
            for (int step = 0; step < 3; ++step) {
                for (int i = 0; i < 10; ++i) {
                    showDialog(frame, i);
                }
                showModalDialog(frame);
                for (int i = 0; i < 10; ++i) {
                    showDialog(frame, i);
                }
            }
        } finally {
            frame.dispose();
        }
    }

    private static void showDialog(final Frame frame, final int i) {
        final  Dialog visible = new Dialog(frame);
        visible.setLocationRelativeTo(null);
        visible.setVisible(true);
        if (i % 2 == 0) {
            new Dialog(frame);
        }
    }

    private static void showModalDialog(final Frame frame) throws Exception {
        final CountDownLatch go = new CountDownLatch(1);
        final Thread thread = new Thread(() -> {
            final Dialog modal = new Dialog(frame, "Modal Dialog", true);
            modal.pack();
            go.countDown();
            modal.setLocationRelativeTo(null);
            modal.setVisible(true);
        });
        thread.start();
        go.await();
    }
}
