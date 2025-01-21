/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d;


import static java2d.CustomControlsContext.State.START;
import java.awt.Component;


/**
 * Demos that animate and have custom controls extend this class.
 */
@SuppressWarnings("serial")
public abstract class AnimatingControlsSurface extends AnimatingSurface
        implements CustomControlsContext {

    @Override
    public void setControls(Component[] controls) {
        this.controls = controls;
    }

    @Override
    public void setConstraints(String[] constraints) {
        this.constraints = constraints;
    }

    @Override
    public String[] getConstraints() {
        return constraints;
    }

    @Override
    public Component[] getControls() {
        return controls;
    }

    @Override
    public void handleThread(CustomControlsContext.State state) {
        for (Component control : controls) {
            if (control instanceof CustomControls) {
                if (state == START) {
                    ((CustomControls) control).start();
                } else {
                    ((CustomControls) control).stop();
                }
            }
        }
    }

    private Component[] controls;
    private String[] constraints = { java.awt.BorderLayout.NORTH };
}
