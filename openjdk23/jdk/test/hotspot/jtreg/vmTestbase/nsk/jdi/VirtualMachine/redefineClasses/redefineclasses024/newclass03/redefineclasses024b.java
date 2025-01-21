/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses024b</code> is deugee's part of the redefineclasses024.
 *  extends a class implements an interface
 */

class redefineclasses024b extends redefineclasses024c implements redefineclasses024i {

    static boolean classPrepare = false;

    public      void dummyMethod01() {
    }
}

interface redefineclasses024i {

    public      void dummyMethod01();
}

class redefineclasses024c {

    public      void dummyMethod01() {
    }
}
