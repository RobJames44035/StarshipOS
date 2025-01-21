/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package java.awt.desktop;

import java.util.EventListener;

/**
 * Common interface for all event listener sub-types. Implementors may implement
 * multiple sub-types, but only need to call
 * {@link java.awt.Desktop#addAppEventListener(SystemEventListener)} once to
 * receive all notifications.
 *
 * @since 9
 */
public interface SystemEventListener extends EventListener {
}
