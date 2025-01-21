/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdi.VirtualMachineManager.createVirtualMachine;


/**
 * This is the target Java VM for the                                  <BR>
 * nsk/jdi/VirtualMachineManager/createVirtualMachine/createVM002 test.<BR>
 *                                                                     <BR>
 */

public class CreateVM002_TargetVM {

    static final int STATUS_PASSED = 0;
    static final int STATUS_FAILED = 2;
    static final int STATUS_TEMP = 95;


    public static void main (String argv[]) {
        System.exit(STATUS_PASSED + STATUS_TEMP);
    }


} // end of CreateVM002_TargetVM class
