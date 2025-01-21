/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package MyPackage;

/**
 * @test
 * @summary Verifies that a VM event callback does not recurse if a VM object is allocated during callback.
 * @requires vm.jvmti
 * @compile VMEventRecursionTest.java
 * @run main/othervm/native -agentlib:VMEventTest MyPackage.VMEventRecursionTest
 */
public class VMEventRecursionTest implements Cloneable {
    // Implement a simple clone. A call will provoke a JVMTI event for VM allocations, which tries to
    // call this again.
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        VMEventRecursionTest obj = new VMEventRecursionTest();
        try {
            obj.clone();
        } catch(CloneNotSupportedException e) {
            // NOP.
        }
    }
}
