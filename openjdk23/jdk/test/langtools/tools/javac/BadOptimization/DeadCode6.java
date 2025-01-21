/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4073244
 * @summary nested classes:  Verify that overzealous dead-code elimination no
 * longer removes live code.
 * @author dps
 *
 * @modules java.desktop
 * @run clean DeadCode6
 * @run compile -O DeadCode6.java
 */

// This class will crash the compiler if the bug recurs.

import java.awt.*;
import java.awt.event.*;

public class DeadCode6 extends Window
{
    public DeadCode6( Frame parent ) {
        super( parent );
    }

    public void init() {
        final DeadCode6 wndThis = this;

        /*******************************************************************
         * add window listener
         ******************************************************************/
        addWindowListener( new WindowAdapter () {
            public void windowClosing( WindowEvent evt ) {
                wndThis.doCancelAction();
            }
        } );
    }

    public void doCancelAction() { }
}
