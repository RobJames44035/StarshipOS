/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg;

import java.util.*;

public class C <E extends Parent> {

    /**
     * Test case one.  This should not cause a line break in the member summary.
     */
    public <T> Object shortTypeParamList(T param){ return null; }

    /**
     * Test case two.  This should cause a line break in the member summary.
     */
    public <W extends String, V extends List> Object longTypeParamList(W param1,
        V param2){ return null; }

    public static void formatDetails(Collection<Map<String,String>> job,
        Collection<String> include) {}
}
