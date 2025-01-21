/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.*;
import java.awt.event.KeyEvent;

/*
  @test
  @key headful
  @summary verify LOCK buttons toogle
  @author Yuri.Nesterenko, Dmitriy.Ermashov
  @library /lib/client
  @build ExtendedRobot
  @run main LockingKeyStateTest
*/

public class LockingKeyStateTest {

    Frame frame;
    ExtendedRobot robot;

    // Note that Kana lock you may actually toggle only if you have one.
    static int[] lockingKeys = { KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_NUM_LOCK,
            KeyEvent.VK_SCROLL_LOCK, KeyEvent.VK_KANA_LOCK };
    boolean[] getSupported = new boolean[lockingKeys.length];
    boolean[] setSupported = new boolean[lockingKeys.length];
    boolean[] state0 = new boolean[lockingKeys.length];

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    LockingKeyStateTest() throws Exception {
        robot = new ExtendedRobot();
        EventQueue.invokeAndWait( this::createGui );
    }

    void toggleAllTrue(){toggleAll(true);}
    void toggleAllFalse(){toggleAll(false);}
    void toggleAll(boolean b) {
        for(int i = 0; i < lockingKeys.length; i++) {
            if(setSupported[i]) {
                toolkit.setLockingKeyState(lockingKeys[i], b);
            }
        }
    }

    void checkAllTrue(){checkAll(true);}
    void checkAllFalse(){checkAll(false);}
    void checkAll(boolean b) {
        for(int i = 0; i < lockingKeys.length; i++) {
            if(getSupported[i]  && setSupported[i]) {
                if (!(toolkit.getLockingKeyState(lockingKeys[i]) == b))
                    throw new RuntimeException("State of "+KeyEvent.getKeyText(lockingKeys[i])+" is not "+b);
                System.out.println("OK, state of "+KeyEvent.getKeyText(lockingKeys[i])+" is "+b);
            }
        }
    }

    void restoreAll() {
        for(int i = 0; i < lockingKeys.length; i++) {
            if(setSupported[i] && getSupported[i]) {
                toolkit.setLockingKeyState(lockingKeys[i], state0[i]);
            }
        }
    }

    public void createGui() {
        for(int i = 0; i < lockingKeys.length; i++) {
            getSupported[i] = false;
            setSupported[i] = false;
            try {
                state0[i] = toolkit.getLockingKeyState(lockingKeys[i]);
                getSupported[i] = true;
                toolkit.setLockingKeyState(lockingKeys[i], state0[i]);
                setSupported[i] = true;
            } catch (UnsupportedOperationException uoe) {
            }
            System.out.println(" State get/set of "+KeyEvent.getKeyText(lockingKeys[i])+" is supported? "+
                    getSupported[i]+", "+setSupported[i]);
        }
        frame = new Frame("LockingKeyStateTest Title");
        frame.setSize(200,200);
        frame.setVisible(true);
    }

    void doTest() throws Exception{
        try {
            robot.waitForIdle();
            robot.mouseMove(frame.getLocationOnScreen().x + frame.getWidth() / 2,
                    frame.getLocationOnScreen().y + frame.getHeight() / 2);
            robot.click();

            EventQueue.invokeAndWait( this::toggleAllTrue );
            robot.waitForIdle(2000);
            EventQueue.invokeAndWait( this::checkAllTrue );
            EventQueue.invokeAndWait( this::toggleAllFalse );
            robot.waitForIdle(2000);
            EventQueue.invokeAndWait( this::checkAllFalse );
        } finally {
            EventQueue.invokeAndWait( this::restoreAll );
            robot.waitForIdle();

            frame.dispose();
        }
    }

    public static void main(String argv[]) throws Exception {
        LockingKeyStateTest af = new LockingKeyStateTest();
        af.doTest();
    }
}
