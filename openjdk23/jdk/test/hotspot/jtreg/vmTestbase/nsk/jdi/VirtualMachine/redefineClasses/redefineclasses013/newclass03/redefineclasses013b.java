/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 * <code>redefineclasses013b</code> is deugee's part of the redefineclasses013.
 * adding new method declared as <code>void newMethod()</code>
 */

public interface redefineclasses013b {

                    void newMethod();

    abstract public void dummyMethod01();

             public void dummyMethod02();

                    void dummyMethod03();
}
