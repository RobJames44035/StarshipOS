/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package org.netbeans.jemmy.drivers.windows;

import java.awt.Component;

import org.netbeans.jemmy.drivers.FrameDriver;
import org.netbeans.jemmy.drivers.InternalFrameDriver;
import org.netbeans.jemmy.drivers.LightSupportiveDriver;
import org.netbeans.jemmy.drivers.WindowDriver;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.JInternalFrameOperator;

/**
 * InternalFrameDriver to do all actions using internal frame APIs.
 *
 * Note: There is no API to get title component, so this driver throws
 *       UnsupportedOperationException for all title component related APIs.
 */
public class InternalFrameAPIDriver extends LightSupportiveDriver
        implements WindowDriver, FrameDriver, InternalFrameDriver {

    public InternalFrameAPIDriver() {
        super(new String[]{"org.netbeans.jemmy.operators.JInternalFrameOperator"});
    }

    @Override
    public void activate(ComponentOperator oper) {
        checkSupported(oper);
        ((JInternalFrameOperator) oper).moveToFront();
        ((JInternalFrameOperator) oper).setSelected(true);
    }

    @Override
    public void maximize(ComponentOperator oper) {
        checkSupported(oper);
        if (!((JInternalFrameOperator) oper).isSelected()) {
            activate(oper);
        }
        ((JInternalFrameOperator) oper).setMaximum(true);
    }

    @Override
    public void demaximize(ComponentOperator oper) {
        checkSupported(oper);
        if (!((JInternalFrameOperator) oper).isSelected()) {
            activate(oper);
        }
        ((JInternalFrameOperator) oper).setMaximum(false);
    }

    @Override
    public void iconify(ComponentOperator oper) {
        checkSupported(oper);
        ((JInternalFrameOperator) oper).setIcon(true);
    }

    @Override
    public void deiconify(ComponentOperator oper) {
        checkSupported(oper);
        ((JInternalFrameOperator) oper).setIcon(false);
    }

    @Override
    public void requestClose(ComponentOperator oper) {
        checkSupported(oper);
        ((JInternalFrameOperator) oper).setClosed(true);
    }

    @Override
    public void move(ComponentOperator oper, int x, int y) {
        checkSupported(oper);
        oper.setLocation(x, y);
    }

    @Override
    public void resize(ComponentOperator oper, int width, int height) {
        checkSupported(oper);
        oper.setSize(width, height);
    }

    @Override
    public Component getTitlePane(ComponentOperator oper) {
        throw new UnsupportedOperationException(
                "There is no way to get title pane of an internal frame.");
    }

    @Override
    public void requestCloseAndThenHide(ComponentOperator oper) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void close(ComponentOperator oper) {
        requestClose(oper);
    }
}
