/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;
import java.util.Locale;

/*
 * @test
 * @summary Check that ComponentOrientation methods do not throw exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessComponentOrientation
 */

public class HeadlessComponentOrientation {
    public static void main(String args[]) {
        ComponentOrientation.LEFT_TO_RIGHT.isHorizontal();
        ComponentOrientation.RIGHT_TO_LEFT.isHorizontal();
        ComponentOrientation.UNKNOWN.isHorizontal();
        ComponentOrientation.LEFT_TO_RIGHT.isLeftToRight();
        ComponentOrientation.RIGHT_TO_LEFT.isLeftToRight();
        ComponentOrientation.UNKNOWN.isLeftToRight();

        for (Locale locale : Locale.getAvailableLocales())
            ComponentOrientation.getOrientation(locale);
    }
}
