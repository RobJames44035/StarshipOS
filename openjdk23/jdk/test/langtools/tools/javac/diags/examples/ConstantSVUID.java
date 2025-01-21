/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.warn.constant.SVUID
// options: -Xlint:serial

import java.io.Serializable;

class ConstantSVUID implements Serializable {
    private static final long serialVersionUID = Integer.parseInt("0");
}
