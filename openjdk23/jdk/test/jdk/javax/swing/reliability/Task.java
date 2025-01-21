/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import javax.swing.*;
import java.awt.*;

/*
 * @author Dmitriy Ermashov (dmitriy.ermashov@oracle.com)
 */

public abstract class Task <T extends Frame> {

    protected T gui;
    protected ExtendedRobot robot;

    public Task(Class guiClass, ExtendedRobot robot) throws Exception {
        this.robot = robot;
        SwingUtilities.invokeAndWait( () -> {
            try {
                this.gui = (T) guiClass.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        robot.waitForIdle(1000);
    }

    public abstract void task() throws Exception;
}
