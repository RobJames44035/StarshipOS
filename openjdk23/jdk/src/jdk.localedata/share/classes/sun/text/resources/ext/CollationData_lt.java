/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_lt extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for lt, accents sorted backwards plus the following: */
                "@" +                                     // tal : french secondary
                "& C < c\u030c , C\u030c " +              // nt : open-o < c-caron
                "& I ; y = \u0131 , Y = \u0130 " +        // nt : i is equivalent to y
                "& S < s\u030c , S\u030c " +              // nt : long-s < s-caron
                "& X < y\u0301, Y\u0301 "+                // nt : x < y-acute
                "< y\u0302 , Y\u0302 < y\u0308, Y\u0308 " + // nt : y-circumflex < y-umlaut
                "& Z < z\u030c , Z\u030c "                // nt : ezh-tail < z-caron
            }
        };
    }
}
