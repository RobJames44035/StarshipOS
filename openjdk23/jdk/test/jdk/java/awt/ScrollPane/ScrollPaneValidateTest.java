/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 8195738
 * @summary scroll position in ScrollPane is reset after calling validate()
 * @run main ScrollPaneValidateTest
 */

import java.awt.ScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Robot;
import java.awt.AWTException;

public class ScrollPaneValidateTest extends Frame {
  ScrollPane pane;

  public ScrollPaneValidateTest() {
    setBounds(300, 300, 300, 300);
    pane = new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
    add(pane, BorderLayout.NORTH);
    pane.add(new InnerPanel());
  }

  public static void main(String[] args) throws AWTException {
    Robot robot = new Robot();
    final ScrollPaneValidateTest obj = new ScrollPaneValidateTest();
    obj.setVisible(true);

    // set to some scroll position
    obj.pane.setScrollPosition(600, 200);

    // get the newly set position
    Point scrollPosition = obj.pane.getScrollPosition();

    // call validate multiple times
    obj.pane.validate();
    robot.delay(1000);
    obj.pane.validate();
    robot.delay(1000);

    // compare position after calling the validate function
    if(!scrollPosition.equals(obj.pane.getScrollPosition())) {
      obj.dispose();
      throw new RuntimeException("Scrolling position is changed in ScrollPane");
    }

    obj.dispose();
    return;
  }

  class InnerPanel extends Panel {
    public InnerPanel() {
      this.setLayout(new GridLayout(2, 4));
      for (int i = 1; i <= 8; i++) {
        this.add(new Button("Button" + i));
      }
    }

    public Dimension getPreferredSize() {
      return new Dimension(980, 200);
    }
  }
}
