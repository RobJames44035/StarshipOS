/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_cs extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for cs, default sorting except for the following: */
                /* add d<stroke> between d and e. */
                /* add ch "ligature" between h and i */
                /* add l<stroke> between l and m. */
                /* add z<abovedot> after z.       */
                "& \u0361 ; \u0308 = \u030d"  // & inv-breve ; umlaut = vt-line-above
                + "& C < c\u030c , C\u030c"  // c < c-caron
                + "& H < ch , cH , Ch , CH " // h < ch ligatures
                + "& R < r\u030c , R\u030c"  // r < r-caron
                + "& S < s\u030c , S\u030c"  // s < s-caron
                + "& Z < z\u030c , Z\u030c"  // z < z-caron
            }
        };
    }
}
