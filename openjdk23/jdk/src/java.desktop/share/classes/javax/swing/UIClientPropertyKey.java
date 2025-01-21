/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package javax.swing;

/**
 * This interface is used only for tagging keys for client properties for
 * {@code JComponent} set by UI which needs to be cleared on {@literal L&F}
 * change and serialization.
 * <p>
 * All such keys are removed from client properties in
 * {@code JComponent.setUI()} method after uninstalling old UI and before
 * installing the new one. They are also removed prior to serialization.
 *
 * @author Igor Kushnirskiy
 * @since 9
 */
public interface UIClientPropertyKey {
}
