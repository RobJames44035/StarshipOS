/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses015b</code> is deugee's part of the redefineclasses015.
 *  adding <code>public</code> modifier to
 *  <code>void dummyMethod03()</code>
 */

public interface redefineclasses015b {

    abstract public void dummyMethod01();

             public void dummyMethod02();

//           ======
             public void dummyMethod03();
//           ======
}
