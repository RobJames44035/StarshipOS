/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

/**
 *  <code>redefineclasses020b</code> is deugee's part of the redefineclasses020.
 *  adding <static> class modifier
 */

public class redefineclasses020b {

    Object obj = new redefineclasses020ib();

    static class redefineclasses020ib {
//  ^^^^^^

        public      void dummyMethod01(){
        }

        protected   void dummyMethod02(){
        }

        private     void dummyMethod03(){
        }
    }
}
