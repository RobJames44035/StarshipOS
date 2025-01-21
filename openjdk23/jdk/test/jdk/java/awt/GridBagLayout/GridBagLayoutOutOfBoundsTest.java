/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
  @test
  @bug 5055696
  @summary REGRESSION: GridBagLayout throws ArrayIndexOutOfBoundsExceptions
  @key headful
  @run main GridBagLayoutOutOfBoundsTest
*/
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Panel;

public class GridBagLayoutOutOfBoundsTest {
    final static int L=2;
    static Frame frame;

    public static void main(String[] args) throws Exception {
        EventQueue.invokeAndWait(() -> {
            try {
                frame = new Frame("GridBagLayoutOutOfBoundsTestFrame");
                frame.validate();
                GridBagLayout layout = new GridBagLayout();
                frame.setLayout(layout);
                GridBagConstraints gridBagConstraints;

                Button[] mb = new Button[L];
                for (int i = 0; i<L; i++){
                    mb[i] = new Button(""+i);
                }
                for (int i = 0; i<mb.length; i++){
                    gridBagConstraints = new GridBagConstraints();
                    gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
                    gridBagConstraints.gridheight = GridBagConstraints.REMAINDER;
                    frame.add(mb[i], gridBagConstraints);
                }
                frame.setVisible(true);
            } finally {
                if (frame != null) {
                    frame.dispose();
                }
            }
        });
    }
}
