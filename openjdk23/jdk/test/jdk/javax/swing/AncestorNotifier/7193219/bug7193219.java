/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 7193219
 * @summary JComboBox serialization fails in JDK 1.7
 * @author Anton Litvinov
 */

import java.io.*;

import javax.swing.*;
import javax.swing.plaf.metal.*;

public class bug7193219 {
    private static byte[] serializeGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Serialization");
        JPanel mainPanel = new JPanel();

        /**
         * If JComboBox is replaced with other component like JLabel
         * The issue does not happen.
         */
        JComboBox status = new JComboBox();
        status.addItem("123");
        mainPanel.add(status);
        frame.getContentPane().add(mainPanel);
        frame.pack();

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(mainPanel);
            oos.flush();
            frame.dispose();
            return baos.toByteArray();
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static void deserializeGUI(byte[] serializedData) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(serializedData));
            JPanel mainPanel = (JPanel)ois.readObject();
            JFrame frame = new JFrame("Deserialization");
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.dispose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new MetalLookAndFeel());
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                deserializeGUI(serializeGUI());
            }
        });
    }
}
