/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package jit.misctests.fpustack;

import java.util.*;
import java.awt.*;
import java.applet.Applet;
import nsk.share.TestFailure;

public class layout implements ilayout {
    public  void formatNodes( Node[] nodes, Dimension d, FontMetrics fm )   {
        int h = d.height/2 - 10 ;

        double alpha = -Math.PI/2;
        for ( int j = 0; j < nodes.length; j++) {
            Node n = nodes[j];
            int w = d.width/2 - fm.stringWidth( n.lbl )/2;

            n.x = d.width/2 + (int)(w*Math.cos( alpha ));

            n.y = d.height/2 + (int)(h*Math.sin( alpha ));
            alpha += 2*Math.PI/nodes.length;
        }
    }

}
