/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
  @test
  @key headful
  @bug 6255653
  @summary REGRESSION: Override isLightweight() causes access violation in awt.dll
  @author Andrei Dmitriev: area=awt-component
  @run main IsLightweightCrash
*/

/*
 * The test may not crash for several times so iteratively continue up to some limit.
 */

import java.awt.*;

public class IsLightweightCrash {
    public static int ITERATIONS = 20;

    public static void main(String []s)
    {
        for (int i = 0; i < ITERATIONS; i++){
            showFrame(i);
        }
    }

    private static void showFrame(int i){
        System.out.println("iteration = "+i);
        Frame f = new Frame();
        f.add(new AHeavyweightComponent());
        f.setVisible(true);
        f.setVisible(false);
    }
}

class AHeavyweightComponent extends Component {
    public boolean isLightweight() { return false; }
}
