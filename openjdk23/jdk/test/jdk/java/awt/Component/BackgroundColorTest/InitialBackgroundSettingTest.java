/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @test
 * @bug 4148334
 * @summary tests that background color is initially set correctly.
 * @requires os.family == "windows"
 * @key headful
 * @run main InitialBackgroundSettingTest
 */
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Scrollbar;
import java.lang.reflect.InvocationTargetException;

public class InitialBackgroundSettingTest {
    Frame frame;
    TextField tf;
    TextArea ta;
    Choice choice;
    List list;
    Scrollbar bar;
    Button button;

    public static void main(String[] args) throws InterruptedException,
            InvocationTargetException {
        InitialBackgroundSettingTest test= new InitialBackgroundSettingTest();
        try {
            EventQueue.invokeAndWait(test::setupGUI);
            EventQueue.invokeAndWait(test::test);
        } finally {
            EventQueue.invokeAndWait(test::dispose);
        }
    }

    public void setupGUI () {
        frame = new Frame("InitialBackgroundSettingTest frame");
        tf = new TextField("I am the TextField");
        ta = new TextArea("I am the TextArea");
        choice = new Choice();
        list = new List();
        bar = new Scrollbar(Scrollbar.HORIZONTAL);
        button = new Button("I am the button");
        frame.setBackground(Color.red);
        frame.setLayout(new GridLayout(7, 1));
        frame.add(button);
        frame.add(bar);
        frame.add(choice);
        frame.add(list);
        frame.add(tf);
        frame.add(ta);
        frame.setVisible(true);
        frame.setBounds (400, 0, 300, 300);
    }

    public void test() {
        boolean passed = true;
        System.out.println("Button background color is:" +
                button.getBackground());
        if (Color.red.equals(button.getBackground())) {
            System.err.println("Button background is red");
            passed = false;
        }
        System.out.println("Scrollbar background color is:" +
                bar.getBackground());
        if (Color.red.equals(bar.getBackground())) {
            System.err.println("ScrollBar background is red");
            passed = false;
        }
        System.out.println("Choice background color is:" +
                choice.getBackground());
        if (Color.red.equals(choice.getBackground())) {
            System.err.println("Choice background is red");
            passed = false;
        }
        System.out.println("List background color is:" +
                list.getBackground());
        if (Color.red.equals(list.getBackground())) {
            System.err.println("List background is red");
            passed = false;
        }
        System.out.println("TextField background color is:" +
                tf.getBackground());
        if (Color.red.equals(tf.getBackground())) {
            System.err.println("TextField background is red");
            passed = false;
        }
        System.out.println("TextArea background color is:" +
                ta.getBackground());
        if (Color.red.equals(ta.getBackground())) {
            System.err.println("TextArea background is red");
            passed = false;
        }

        if (!passed) {
            throw new RuntimeException("One or more component inherited" +
                    " background from a Frame");
        }
    }

    public void dispose() {
        frame.dispose();
    }
}
