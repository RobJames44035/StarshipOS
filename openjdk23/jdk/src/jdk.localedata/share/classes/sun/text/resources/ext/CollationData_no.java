/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_no extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                "& D <\u00D0,\u00F0" +      // eth
                "& ss,\u00DF" +             // s-zet
                "& y , u\u0308" +   // u-umlaut is eq. to y.
                "& y ; U\u0308" +  // u-umlaut is eq. to y.
                "& Z < \u00e6, \u00c6 " +    // z < z-caron
                " < a\u0308, A\u0308" +      // nt : a-umlaut
                "< \u00f8, \u00d8 < o\u0308, O\u0308" + // nt : o-stroke < o-umlaut
                "< o\u030b, O\u030b " +      // nt : o-double-acute
                "< a\u030a, A\u030a" +       // nt : a-ring
                ", aa , aA , Aa , AA " +         // tal : aa ligature sorts after a-ring
                " & V < w, W "
            }
        };
    }
}
