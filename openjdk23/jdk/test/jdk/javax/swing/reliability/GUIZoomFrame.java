/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * @author Aruna Samji
 */

public class GUIZoomFrame extends Frame {

    JFrame jframe1, jframe2;
    JButton jbutton;
    JTextArea jtextarea;
    volatile boolean maxHor, maxVer, maxBoth, normal, iconify;

    public GUIZoomFrame() {
        //GUI for ZoomJFrameChangeState
        jframe1 = new JFrame("ZoomJFrameChangeState");
        jframe1.getContentPane().setBackground(Color.red);
        jframe1.getContentPane().setLayout(null);
        jframe1.setSize(500,270);

        //GUI for ZoomJFrameRepaint
        jframe2 = new JFrame("ZoomJFrameRepaint");
        jframe2.getContentPane().setBackground(Color.red);
        jframe2.setSize(500, 270);
        jframe2.getContentPane().setLayout(null);
        jbutton = new JButton("TestButton");
        jtextarea = new JTextArea("TextArea");
        jbutton.setBounds(20, 100, 80, 60);
        jtextarea.setBounds(120, 100, 80, 60);

        //Listeners for ZoomJFrameChangeState
        jframe1.addWindowStateListener(new WindowAdapter() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == Frame.MAXIMIZED_BOTH)
                    maxBoth = true;

                if (e.getNewState() == Frame.NORMAL)
                    normal = true;

                if (e.getNewState() == Frame.ICONIFIED)
                    iconify = true;

                if (e.getNewState() == Frame.MAXIMIZED_HORIZ)
                    maxHor = true;

                if (e.getNewState() == Frame.MAXIMIZED_VERT)
                    maxVer = true;
            }
        });

        //Listeners for ZoomJFrameRepaint
        jframe2.addWindowStateListener( new WindowAdapter() {
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == Frame.MAXIMIZED_BOTH)
                    maxBoth = true;

                if (e.getNewState() == Frame.NORMAL)
                    normal = true;
            }
        });
    }
}
