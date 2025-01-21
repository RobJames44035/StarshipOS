/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.awt.desktop;

/**
 * Implementors receive notification when the displays attached to the system
 * have entered power save sleep.
 * <p>
 * This notification is useful for discontinuing a costly animation, or
 * indicating that the user is no longer present on a network service.
 *
 * @since 9
 */
public interface ScreenSleepListener extends SystemEventListener {

    /**
     * Called when the system displays have entered power save sleep.
     *
     * @param  e the screen sleep event
     */
    public void screenAboutToSleep(ScreenSleepEvent e);

    /**
     * Called when the system displays have awoken from power save sleep.
     *
     * @param  e the screen sleep event
     */
    public void screenAwoke(ScreenSleepEvent e);
}
