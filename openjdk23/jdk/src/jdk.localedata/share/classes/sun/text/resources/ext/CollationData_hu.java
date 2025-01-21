/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_hu extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                /* for hu, default sorting except for the following: */
                /* add cs "ligature" between c and d. */
                /* add d<stroke> between d and e. */
                /* add gy "ligature" between g and h. */
                /* add ly "ligature" between l and l<stroke>. */
                /* add l<stroke> between l and m. */
                /* add sz "ligature" between s and t. */
                /* add zs "ligature" between z and z<abovedot> */
                /* add z<abovedot> after z.       */
                "& C < cs , cS , Cs , CS " // cs ligatures
                + "& D < \u0111, \u0110 "    // tal : african d < d-stroke
                + "& G < gy, Gy, gY, GY "    // gy ligatures
                + "& L < ly, Ly, lY, LY "    // l < ly
                + "& O < o\u0308 , O\u0308 " // O < o-umlaut
                + "< o\u030b , O\u030b "     // o-double-accute
                + "& S < sz , sZ , Sz , SZ " // s < sz ligature
                + "& U < u\u0308 , U\u0308 " // u < u-umlaut
                + "< u\u030b , U\u030b "     // u-double-accute
                + "& Z < zs , zS , Zs , ZS " // stop-stroke < zs ligature
            }
        };
    }
}
