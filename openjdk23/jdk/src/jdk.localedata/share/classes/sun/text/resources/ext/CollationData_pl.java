/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_pl extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for pl, default sorting except for the following: */
                /* add d<stroke> between d and e. */
                /* add l<stroke> between l and m. */
                /* add z<abovedot> after z.       */
                "& A < a\u0328 , A\u0328 " +      // a < a-ogonek
                "& C < c\u0301 , C\u0301 " +      // c < c-acute
                "& D < \u0111, \u0110 " +         // tal : d < d-stroke
                "& E < e\u0328 , E\u0328 " +      // e < e-ogonek
                "& L < \u0142 , \u0141 " +        // l < l-stroke
                "& N < n\u0301 , N\u0301 " +      // n < n-acute
                "& O < o\u0301 , O\u0301 " +      // o < o-acute
                "& S < s\u0301 , S\u0301 " +      // s < s-acute
                "& Z < z\u0301 , Z\u0301 " +      // z < z-acute
                "< z\u0307 , Z\u0307 "            // z-dot-above
            }
        };
    }
}
