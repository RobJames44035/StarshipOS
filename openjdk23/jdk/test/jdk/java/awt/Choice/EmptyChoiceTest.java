/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 @test
 @bug 4908468
 @summary Linux Empty Choice throws NPE
 @key headful
 @run main EmptyChoiceTest
*/
import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.lang.reflect.InvocationTargetException;

public class EmptyChoiceTest
{
    Frame frame;
    Choice choice = null;

    public static void main(String[] args) throws
            InterruptedException,
            InvocationTargetException {
        EventQueue.invokeAndWait(() -> {
            EmptyChoiceTest emptyChoiceTest = new EmptyChoiceTest();
            emptyChoiceTest.init();
            emptyChoiceTest.test();
        });
    }

    public void init() {
        frame = new Frame();
        frame.setLayout(new BorderLayout());
        choice = new Choice();
        frame.add(choice, BorderLayout.NORTH);
        frame.setSize(200, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.validate();
    }

    public void test () {
        try {
            int iWidth = choice.getWidth();
            int iHeight = choice.getHeight();
            Image componentImage =
                choice.createImage(iWidth, iHeight);
            Graphics graphics =
                componentImage.getGraphics();
            graphics.setClip(0, 0, iWidth, iHeight);
            choice.printAll(graphics);
            System.out.println("PrintAll successful!");
        } catch (NullPointerException exp) {
            throw new RuntimeException("Test failed. " +
                    "Empty Choice printAll throws NullPointerException");
        } catch (Exception exc){
            throw new RuntimeException("Test failed.", exc);
        } finally {
            frame.dispose();
        }
    }
}
