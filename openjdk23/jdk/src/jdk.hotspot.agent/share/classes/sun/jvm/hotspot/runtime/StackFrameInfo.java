/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package sun.jvm.hotspot.runtime;

import sun.jvm.hotspot.oops.*;

public class StackFrameInfo {
    private Method      method;
    int                 bci;
    Oop                 classHolder;

    public StackFrameInfo(JavaVFrame vf) {
        this.method = vf.getMethod();
        this.bci = vf.getBCI();
    }

    public Method getMethod() {
        return method;
    }

    public int getBCI() {
        return bci;
    }
}
