/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package sun.jvm.hotspot.utilities;

/**
 * A simple class for sending updates to objects.
 * The design of this class is based on the deprecated java.util.Observer.
 */
public interface Observer {
    /**
     * This method is called whenever an update is needed.
     */
    void update(Observable o, Object arg);
}
