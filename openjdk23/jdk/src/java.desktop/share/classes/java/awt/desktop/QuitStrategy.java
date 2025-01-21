/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.awt.desktop;

/**
 * The strategy used to shut down the application, if Sudden Termination is not
 * enabled.
 *
 * @see java.awt.Desktop#setQuitHandler(QuitHandler)
 * @see java.awt.Desktop#setQuitStrategy(QuitStrategy)
 * @see java.awt.Desktop#enableSuddenTermination()
 * @see java.awt.Desktop#disableSuddenTermination()
 * @since 9
 */
public enum QuitStrategy {
    /**
     * Shuts down the application by calling {@code System.exit(0)}. This is the
     * default strategy.
     */
    NORMAL_EXIT,

    /**
     * Shuts down the application by closing each window from back-to-front.
     */
    CLOSE_ALL_WINDOWS
}
