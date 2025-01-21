/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */


package java2d;


/**
 * Demos that animate extend this class.
 */
@SuppressWarnings("serial")
public abstract class AnimatingSurface extends Surface implements Runnable {

    private volatile boolean running = false;

    private volatile Thread thread;

    public abstract void step(int w, int h);

    public abstract void reset(int newwidth, int newheight);


    public synchronized void start() {
        if (!running() && !dontThread) {
            thread = new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.setName(name + " Demo");
            thread.start();
            running = true;
        }
    }


    public synchronized void stop() {
        if (thread != null) {
            running = false;
            thread.interrupt();
        }
        thread = null;
        notifyAll();
    }


    @Override
    @SuppressWarnings("SleepWhileHoldingLock")
    public void run() {

        while (running() && !isShowing() || getSize().width == 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }
        }

        while (running()) {
            repaint();
            try {
                Thread.sleep(sleepAmount);
            } catch (InterruptedException ignored) {
            }
        }
        synchronized (this) {
            running = false;
        }
    }

    /**
     * @return the running
     */
    public synchronized boolean running() {
        return running;
    }

    /**
     * Causes surface to repaint immediately
     */
    public synchronized void doRepaint() {
        if (running() && thread != null) {
            thread.interrupt();
        }
    }
}
