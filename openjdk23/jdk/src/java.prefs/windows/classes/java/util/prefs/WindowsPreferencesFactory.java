/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */
package java.util.prefs;

/**
 * Implementation of  {@code PreferencesFactory} to return
 * WindowsPreferences objects.
 *
 * @author  Konstantin Kladko
 * @see Preferences
 * @see WindowsPreferences
 * @since 1.4
 */
class WindowsPreferencesFactory implements PreferencesFactory  {

    /**
     * Returns WindowsPreferences.userRoot
     */
    public Preferences userRoot() {
        return WindowsPreferences.getUserRoot();
    }

    /**
     * Returns WindowsPreferences.systemRoot
     */
    public Preferences systemRoot() {
        return WindowsPreferences.getSystemRoot();
    }
}
