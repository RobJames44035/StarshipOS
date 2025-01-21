/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_tr extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                "& A < a\u0308 , A\u0308 "   // a-umlaut sorts between a and b
                + "& C < c\u0327 , C\u0327 " // c-cedilla sorts between c and d
                + "& G < g\u0306 , G\u0306 " // g-breve sorts between g and h
                + "& H < \u0131 , I "  // dotless i, I
                + "& I < i , \u0130 "  // dotted i, I
                + "< \u0132 , \u0133 "       // ij ligature sorts between i and j
                + "& O < o\u0308 , O\u0308 " // o-umlaut sorts between o and p
                + "& S < s\u0327 , S\u0327 " // s-cedilla sorts between s and t
                + "& U < u\u0308 , U\u0308 " // u-umlaut sorts between u and v
            }
        };
    }
}
