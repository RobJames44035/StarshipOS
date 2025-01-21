/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

import java.util.prefs.Preferences;

/**
 * CheckUserPrefsStorage.java uses this to check that preferences stored
 * by CheckUserPrefFirst.java can be retrieved
 */

public class CheckUserPrefLater {

    public static void main(String[] args) throws Exception {
        Preferences prefs = Preferences.userNodeForPackage(CheckUserPrefFirst.class);
        String result = prefs.get("Check", null);
        if ((result == null) || !(result.equals("Success")))
            throw new RuntimeException("User pref not stored!");
        prefs.remove("Check");
        prefs.flush();
    }

}

