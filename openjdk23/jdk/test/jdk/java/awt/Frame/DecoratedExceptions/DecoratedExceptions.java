/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @key headful
 * @summary An attempt to set non-trivial background, shape, or translucency
 *          to a decorated toplevel should end with an exception.
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 * @library /lib/client
 * @build ExtendedRobot
 * @run main DecoratedExceptions
 */
public class DecoratedExceptions {
    public static void main(String args[]) throws Exception{
        ExtendedRobot robot = new ExtendedRobot();
        Toolkit.getDefaultToolkit().getSystemEventQueue().invokeAndWait(() -> {
            Frame frame = new Frame("Frame");
            frame.setBounds(50,50,400,200);
            try {
                frame.setOpacity(0.5f);
                throw new RuntimeException("No exception when Opacity set to a decorated Frame");
            }catch(IllegalComponentStateException e) {
            }
            try {
                frame.setShape(new Rectangle(50,50,400,200));
                throw new RuntimeException("No exception when Shape set to a decorated Frame");
            }catch(IllegalComponentStateException e) {
            }
            try {
                frame.setBackground(new Color(50, 50, 50, 100));
                throw new RuntimeException("No exception when Alpha background set to a decorated Frame");
            }catch(IllegalComponentStateException e) {
            }
            frame.setVisible(true);
            Dialog dialog = new Dialog( frame );
            try {
                dialog.setOpacity(0.5f);
                throw new RuntimeException("No exception when Opacity set to a decorated Dialog");
            }catch(IllegalComponentStateException e) {
            }
            try {
                dialog.setShape(new Rectangle(50,50,400,200));
                throw new RuntimeException("No exception when Shape set to a decorated Dialog");
            }catch(IllegalComponentStateException e) {
            }
            try {
                dialog.setBackground(new Color(50, 50, 50, 100));
                throw new RuntimeException("No exception when Alpha background set to a decorated Dialog");
            }catch(IllegalComponentStateException e) {
            }
            dialog.setVisible(true);
        });
        robot.waitForIdle(1000);
    }
}
