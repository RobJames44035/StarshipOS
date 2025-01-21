/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.input;

import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.drivers.MouseDriver;
import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * MouseDriver using robot operations.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class MouseRobotDriver extends RobotDriver implements MouseDriver {

    /**
     * Constructs a MouseRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     */
    public MouseRobotDriver(Timeout autoDelay) {
        super(autoDelay);
    }

    /**
     * Constructs a MouseRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     * @param smooth - whether to move mouse smooth from one ppoint to another.
     */
    public MouseRobotDriver(Timeout autoDelay, boolean smooth) {
        super(autoDelay, smooth);
    }

    /**
     * Constructs a MouseRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     * @param supported an array of supported class names
     */
    public MouseRobotDriver(Timeout autoDelay, String[] supported) {
        super(autoDelay, supported);
    }

    /**
     * Constructs a MouseRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     * @param supported an array of supported class names
     * @param smooth - whether to move mouse smooth from one ppoint to another.
     */
    public MouseRobotDriver(Timeout autoDelay, String[] supported, boolean smooth) {
        super(autoDelay, supported, smooth);
    }

    @Override
    public void pressMouse(ComponentOperator oper, int x, int y, int mouseButton, int modifiers) {
        pressMouse(mouseButton, modifiers);
    }

    @Override
    public void releaseMouse(ComponentOperator oper, int x, int y, int mouseButton, int modifiers) {
        releaseMouse(mouseButton, modifiers);
    }

    @Override
    public void moveMouse(ComponentOperator oper, int x, int y) {
        moveMouse(getAbsoluteX(oper, x), getAbsoluteY(oper, y));
    }

    @Override
    public void clickMouse(ComponentOperator oper, int x, int y, int clickCount, int mouseButton,
            int modifiers, Timeout mouseClick) {
        clickMouse(getAbsoluteX(oper, x), getAbsoluteY(oper, y), clickCount, mouseButton, modifiers, mouseClick);
    }

    @Override
    public void dragMouse(ComponentOperator oper, int x, int y, int mouseButton, int modifiers) {
        moveMouse(getAbsoluteX(oper, x), getAbsoluteY(oper, y));
    }

    @Override
    public void dragNDrop(ComponentOperator oper, int start_x, int start_y, int end_x, int end_y,
            int mouseButton, int modifiers, Timeout before, Timeout after) {
        dragNDrop(getAbsoluteX(oper, start_x), getAbsoluteY(oper, start_y), getAbsoluteX(oper, end_x), getAbsoluteY(oper, end_y), mouseButton, modifiers, before, after);
    }

    @Override
    public void enterMouse(ComponentOperator oper) {
        moveMouse(oper, oper.getCenterXForClick(), oper.getCenterYForClick());
    }

    @Override
    public void exitMouse(ComponentOperator oper) {
        //better not go anywhere
        //exit will be executed during the next
        //mouse move anyway.
        //        moveMouse(oper, -1, -1);
    }

    /**
     * Returns absolute x coordinate for relative x coordinate.
     *
     * @param oper an operator
     * @param x a relative x coordinate.
     * @return an absolute x coordinate.
     */
    protected int getAbsoluteX(ComponentOperator oper, int x) {
        return oper.getSource().getLocationOnScreen().x + x;
    }

    /**
     * Returns absolute y coordinate for relative y coordinate.
     *
     * @param oper an operator
     * @param y a relative y coordinate.
     * @return an absolute y coordinate.
     */
    protected int getAbsoluteY(ComponentOperator oper, int y) {
        return oper.getSource().getLocationOnScreen().y + y;
    }
}
