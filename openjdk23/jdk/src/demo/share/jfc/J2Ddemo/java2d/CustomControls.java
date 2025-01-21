/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


package java2d;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;


/**
 * A convenience class for demos that use Custom Controls.  This class
 * sets up the thread for running the custom control.  A notifier thread
 * is started as well, a flashing 2x2 rect is drawn in the upper right corner
 * while the custom control thread continues to run.
 */
@SuppressWarnings("serial")
public abstract class CustomControls extends JPanel implements Runnable {


    protected Thread thread;
    protected boolean doNotifier;
    private CCNotifierThread ccnt;
    private String name = "foo.bar Demo";
    private static final Color blue = new Color(204, 204, 255);


    public CustomControls() {
        setBorder(new EtchedBorder());
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (thread == null) { start(); } else { stop(); }
            }
        });
    }

    public CustomControls(String name) {
        this();
        this.name = name + " Demo";
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(doNotifier ? blue : Color.gray);
        g.fillRect(getSize().width-2, 0, 2, 2);
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setName(name + " ccthread");
            thread.start();
            (ccnt = new CCNotifierThread()).start();
            ccnt.setName(name + " ccthread notifier");
        }
    }

    public synchronized void stop() {
        if (thread != null) {
            thread.interrupt();
            if (ccnt != null) {
                ccnt.interrupt();
            }
        }
        thread = null;
    }


    // Custom Controls override the run method
    @Override
    public void run() {
    }


    /**
     * Notifier that the custom control thread is running.
     */
    class CCNotifierThread extends Thread {

        @Override
        @SuppressWarnings("SleepWhileHoldingLock")
        public void run() {
            while (thread != null) {
                doNotifier = !doNotifier;
                repaint();
                try {
                    Thread.sleep(444);
                } catch (Exception ex) {
                    break;
                }
            }
            doNotifier = false; repaint();
        }
    }
}
