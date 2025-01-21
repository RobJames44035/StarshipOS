/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package pkg1;

import pkg2.*;
import java.io.Serializable;

/**
 * @serial This is the serial tag's comment.
 */
public class C1 implements Serializable {

    public C2 field;

    public static final String CONSTANT1 = "C1";

    public C2 method(C2 param) {
        return param;
    }

    /**
     * @deprecated don't use this anymore.
     */
    public void deprecatedMethod() {}
}
