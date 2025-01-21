/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


package java2d;


import java.awt.Component;


/**
 * ControlsSurface or AnimatingControlsSurface classes implement
 * this interface.
 */
public interface CustomControlsContext {
    public static enum State { START, STOP };
    public String[] getConstraints();
    public Component[] getControls();
    public void setControls(Component[] controls);
    public void setConstraints(String[] constraints);
    public void handleThread(CustomControlsContext.State state);
}
