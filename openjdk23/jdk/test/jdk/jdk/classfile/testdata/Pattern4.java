/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package testdata;

public class Pattern4 {

    static void troublesCausingMethod() {
        try {
            Object o = null;
            try {
                o = null;
            } catch (Exception e) {
                if (o != null) o = null;
            }
            if (o != null) {}
        } catch (Exception e) {}
    }
}
