/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.awt.desktop;

import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.io.Serial;

/**
 * Event sent when the system enters and exits power save sleep.
 *
 * @see SystemSleepListener#systemAboutToSleep(SystemSleepEvent)
 * @see SystemSleepListener#systemAwoke(SystemSleepEvent)
 * @since 9
 */
public final class SystemSleepEvent extends AppEvent {

    /**
     * Use serialVersionUID from JDK 9 for interoperability.
     */
    @Serial
    private static final long serialVersionUID = 11372269824930549L;

    /**
     * Constructs a {@code SystemSleepEvent}.
     *
     * @throws HeadlessException if {@link GraphicsEnvironment#isHeadless()}
     *         returns {@code true}
     * @throws UnsupportedOperationException if Desktop API is not supported on
     *         the current platform
     * @see Desktop#isDesktopSupported()
     * @see java.awt.GraphicsEnvironment#isHeadless
     */
    public SystemSleepEvent() {
    }
}
