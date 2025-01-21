/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy.drivers.input;

import org.netbeans.jemmy.Timeout;
import org.netbeans.jemmy.drivers.KeyDriver;
import org.netbeans.jemmy.operators.ComponentOperator;

/**
 * KeyDriver using robot operations.
 *
 * @author Alexandre Iline(alexandre.iline@oracle.com)
 */
public class KeyRobotDriver extends RobotDriver implements KeyDriver {

    /**
     * Constructs a KeyRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     */
    public KeyRobotDriver(Timeout autoDelay) {
        super(autoDelay);
    }

    /**
     * Constructs a KeyRobotDriver object.
     *
     * @param autoDelay Time for {@code Robot.setAutoDelay(long)} method.
     * @param supported an array of supported class names
     */
    public KeyRobotDriver(Timeout autoDelay, String[] supported) {
        super(autoDelay, supported);
    }

    @Override
    public void pushKey(ComponentOperator oper, int keyCode, int modifiers, Timeout pushTime) {
        pressKey(oper, keyCode, modifiers);
        pushTime.sleep();
        releaseKey(oper, keyCode, modifiers);
    }

    @Override
    public void typeKey(ComponentOperator oper, int keyCode, char keyChar, int modifiers, Timeout pushTime) {
        pushKey(oper, keyCode, modifiers, pushTime);
    }

    /**
     * Presses a key.
     *
     * @param oper Operator to press a key on.
     * @param keyCode Key code ({@code KeyEventVK_*} field.
     * @param modifiers a combination of {@code InputEvent.*_MASK} fields.
     */
    @Override
    public void pressKey(ComponentOperator oper, int keyCode, int modifiers) {
        pressKey(keyCode, modifiers);
    }

    @Override
    public void typedKey(ComponentOperator oper, int keyCode, char keyChar, int modifiers) {
        releaseKey(oper, keyCode, modifiers);
    }

    /**
     * Releases a key.
     *
     * @param oper Operator to release a key on.
     * @param keyCode Key code ({@code KeyEventVK_*} field.
     * @param modifiers a combination of {@code InputEvent.*_MASK} fields.
     */
    @Override
    public void releaseKey(ComponentOperator oper, int keyCode, int modifiers) {
        releaseKey(keyCode, modifiers);
    }
}
