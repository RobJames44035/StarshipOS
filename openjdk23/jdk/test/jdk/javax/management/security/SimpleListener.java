/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

// JDK
import java.util.Vector;

// JMX
import javax.management.NotificationListener;
import javax.management.Notification;

public class SimpleListener implements NotificationListener {
    private boolean received = false;
    private String type = null;
    private Object handback = null;
    private Vector<Object> handbacks = new Vector<Object>();
    private int nbrec = 0;

    public synchronized void handleNotification(Notification notification,
                                                Object handback) {
        Utils.debug(Utils.DEBUG_STANDARD,
            "SimpleListener::handleNotification :" + notification);
        try {
            received = true;
            type = notification.getType();
            this.handback = handback;
            handbacks.add(handback);
            nbrec++;
            notify();
        } catch(Exception e) {
            System.out.println("(ERROR) SimpleListener::handleNotification :"
                        + " Caught exception "
                        + e) ;
        }
    }

    public synchronized boolean isNotificationReceived() {
        boolean ret = received;
        reset();
        return ret;
    }

    public synchronized Object[] waitForMultiNotifications(int nb) {
        while(true) {
            if(nbrec < nb) {
                Utils.debug(Utils.DEBUG_STANDARD,
                            "SimpleListener::waitForMultiNotifications wait");
                try {
                    wait();
                } catch(InterruptedException ie) {
                    // OK : we wait for being interrupted
                }
                Utils.debug(Utils.DEBUG_STANDARD,
                            "SimpleListener::waitForMultiNotifications wait over");
            }
            else
            break;
        }
        Object[] ret = handbacks.toArray();
        reset();
        return ret;
    }

    private void reset() {
        received = false;
        handback = null;
        handbacks.removeAllElements();
        type = null;
    }

    public synchronized Object waitForNotificationHB() {
        while(true) {
            if(!received) {
                Utils.debug(Utils.DEBUG_STANDARD,
                    "SimpleListener::waitForNotificationHB wait");
                try {
                    wait();
                } catch(InterruptedException ie) {
                    // OK : we wait for being interrupted
                }
                Utils.debug(Utils.DEBUG_STANDARD,
                    "SimpleListener::waitForNotificationHB received");
            }
            else
                break;
        }
        Object ret = handback;
        reset();
        return ret;
    }

    public synchronized String waitForNotification() {
        while(true) {
            if(!received) {
                Utils.debug(Utils.DEBUG_STANDARD,
                    "SimpleListener::waitForNotification wait");
                try {
                    wait();
                } catch(InterruptedException ie) {
                    // OK : we wait for being interrupted
                }
                Utils.debug(Utils.DEBUG_STANDARD,
                    "SimpleListener::waitForNotification received");
            }
            else
                break;
        }
        String ret = type;
        reset();
        return ret;
    }
}
