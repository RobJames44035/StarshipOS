/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 */

package sun.text.resources.ext;

import java.util.ListResourceBundle;

public class CollationData_fi extends ListResourceBundle {

    protected final Object[][] getContents() {
        return new Object[][] {
            { "Rule",
                "& V ; w , W "
                + "& Z < a\u030a , A\u030a"   // Z < a-ring
                + "< a\u0308 , A\u0308 < o\u0308 , O\u0308"
                + "< o\u030b , O\u030b ; \u00f8 , \u00d8"  // o-double-acute ; o-stroke
                + "&  Y ; u\u030b, U\u030b "       // nt : y ; u-double-acute
                + "; u\u0308 , U\u0308"   // nt & tal : y ; u-umlaut
            }
        };
    }
}
