/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */


package com.sun.java.swing.plaf.motif;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.event.*;

/**
 * Button Listener
 *
 * @author Rich Schiavi
 */
public class MotifButtonListener extends BasicButtonListener {
    public MotifButtonListener(AbstractButton b ) {
        super(b);
    }

    protected void checkOpacity(AbstractButton b) {
        b.setOpaque( false );
    }
}
