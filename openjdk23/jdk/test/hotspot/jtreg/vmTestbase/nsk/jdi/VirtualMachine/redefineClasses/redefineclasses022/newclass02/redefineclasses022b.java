/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses022b</code> is deugee's part of the redefineclasses022.
 *  changing <public> class modifier to <private>
 */

public class redefineclasses022b {

    Object obj = new redefineclasses022ib();

    private class redefineclasses022ib {
//  ^^^^^^^

        public      void dummyMethod01(){
        }

        protected   void dummyMethod02(){
        }

        private     void dummyMethod03(){
        }
    }
}
