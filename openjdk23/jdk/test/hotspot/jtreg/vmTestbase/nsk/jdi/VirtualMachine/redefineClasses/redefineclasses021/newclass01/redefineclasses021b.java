/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package nsk.jdi.VirtualMachine.redefineClasses;

/**
 *  <code>redefineclasses021b</code> is deugee's part of the redefineclasses021.
 *  adding <code>public</code> modifier
 */

public class redefineclasses021b {

    redefineclasses021bc obj = new redefineclasses021bc();

    public interface redefineclasses021bi {
//  ^^^^^^
        void dummyMethod01();

    }

    public interface redefineclasses021bir {
        void dummyMethod01();

    }

    class redefineclasses021bc implements redefineclasses021bi, redefineclasses021bir {

        public void dummyMethod01() {
        }

    }
}
