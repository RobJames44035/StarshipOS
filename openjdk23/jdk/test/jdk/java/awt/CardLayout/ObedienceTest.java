/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
  @test
  @bug 4690266
  @summary REGRESSION: Wizard Page does not move to the next page
*/

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;

public class ObedienceTest {

    public static void main(String[] args) {
        Container cont = new Container();
        Component comp1 = new Component() {};
        Component comp2 = new Component() {};
        CardLayout layout = new CardLayout();
        cont.setLayout(layout);
        cont.add(comp1, "first");
        cont.add(comp2, "second");

        if (!comp1.isVisible()) {
            throw new RuntimeException("first component must be visible");
        }

        comp1.setVisible(false);
        comp2.setVisible(true);
        layout.layoutContainer(cont);

        if (!comp2.isVisible() || comp1.isVisible()) {
            System.out.println("comp1.isVisible() = " + comp1.isVisible());
            System.out.println("comp2.isVisible() = " + comp2.isVisible());
            throw new RuntimeException("manually shown component must be visible after layoutContainer()");
        }
    }
}
