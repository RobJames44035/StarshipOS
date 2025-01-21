/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 6179222 6271692
 * @summary Tests complex property name
 * @author Scott Violet
 */

import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.beans.EventHandler;

public class Test6179222 {
    private Bar bar = new Bar();

    public static void main(String[] args) {
        Test6179222 test = new Test6179222();
        // test 6179222
        test(EventHandler.create(ActionListener.class, test, "foo", "source.icon"));
        // test 6265540
        test(EventHandler.create(ActionListener.class, test, "bar.doit"));
        if (!test.bar.invoked) {
            throw new Error("Bar was not set");
        }
    }

    private static void test(ActionListener listener) {
        JButton button = new JButton("hi");
        button.addActionListener(listener);
        button.doClick();
    }

    public void foo(Icon o) {
    }

    public Bar getBar() {
        return this.bar;
    }

    public static class Bar {
        private boolean invoked;

        public void doit() {
            this.invoked = true;
        }
    }
}
