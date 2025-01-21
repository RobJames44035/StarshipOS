/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
  @test
  @bug 4087971
  @summary Insets does not layout a component correctly
  @key headful
  @run main InsetsTest
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class InsetsTest {
    private int leftInsetValue;
    private InsetsClass IC;

    public static void main(String[] args) throws Exception {
        InsetsTest test = new InsetsTest();
        test.start();
    }

    public void start() throws Exception {
        EventQueue.invokeAndWait(() -> {
            try {
                IC = new InsetsClass();
                IC.setLayout(new BorderLayout());
                IC.setSize(200, 200);
                IC.setVisible(true);

                leftInsetValue = IC.returnLeftInset();
                if (leftInsetValue != 30) {
                    throw new RuntimeException("Test Failed - Left inset" +
                            "is not taken correctly");
                }
            } finally {
                if (IC != null) {
                    IC.dispose();
                }
            }
        });
    }
}

class InsetsClass extends JFrame {
    private int value;
    private JPanel panel;

    public InsetsClass() {
        super("TestFrame");
        setBackground(Color.lightGray);

        panel = new JPanel();
        panel.setBorder(new EmptyBorder(new Insets(30, 30, 30, 30)));
        panel.add(new JButton("Test Button"));

        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

    public int returnLeftInset() {
        // Getting the left inset value
        Insets insets = panel.getInsets();
        value = insets.left;
        return value;
    }
}
