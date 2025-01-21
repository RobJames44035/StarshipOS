/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/* @test
 * @bug 7155298
 * @key headful
 * @run main/othervm/timeout=60 TestDispose
 * @summary Editable TextField blocks GUI application from exit.
 * @author Sean Chou
 */

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.Robot;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class TestDispose {

    public static Frame frame = null;
    public static TextField textField = null;
    public static volatile Process worker = null;

    public void testDispose() throws InvocationTargetException,
            InterruptedException {
        Robot robot;
        try {
            robot = new Robot();
        }catch(Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unexpected failure");
        }

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame = new JFrame("Test");

                textField = new TextField("editable textArea");
                textField.setEditable(true);
                // textField.setEditable(false); // this testcase passes if textField is non-editable

                frame.setLayout(new FlowLayout());
                frame.add(textField);

                frame.pack();
                frame.setVisible(true);
            }
        });
        robot.waitForIdle();

        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                frame.dispose();
            }
        });
        robot.waitForIdle();

    }

    public static void main(String[] args) throws Exception{
        if(args.length == 0) {
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run() {
                    worker.destroy();
                }
            });

            System.out.println(System.getProperty("java.home")+"/bin/java TestDispose workprocess");
            worker = Runtime.getRuntime().exec(System.getProperty("java.home")+"/bin/java TestDispose workprocess");
            worker.waitFor();
            return;
        }

        TestDispose app = new TestDispose();
        app.testDispose();
    }

}
