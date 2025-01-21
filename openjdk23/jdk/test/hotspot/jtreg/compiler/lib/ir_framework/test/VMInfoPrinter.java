/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package compiler.lib.ir_framework.test;

import compiler.lib.ir_framework.shared.TestFrameworkSocket;
import jdk.test.whitebox.WhiteBox;

/**
 * Prints some test VM info to the socket.
 */
public class VMInfoPrinter {
    public static final String START_VM_INFO = "##### IRMatchingVMInfo - used by TestFramework #####";
    public static final String END_VM_INFO = "----- END VMInfo -----";

    private static final WhiteBox WHITE_BOX = WhiteBox.getWhiteBox();

    public static void emit() {
        StringBuilder vmInfo = new StringBuilder();
        vmInfo.append(START_VM_INFO).append(System.lineSeparator());
        vmInfo.append("<key>:<value>").append(System.lineSeparator());

        // CPU feature independent info
        String cpuFeatures = WHITE_BOX.getCPUFeatures();
        vmInfo.append("cpuFeatures:").append(cpuFeatures).append(System.lineSeparator());
        long maxVectorSize = WHITE_BOX.getIntxVMFlag("MaxVectorSize");
        vmInfo.append("MaxVectorSize:").append(maxVectorSize).append(System.lineSeparator());
        boolean maxVectorSizeIsDefault = WHITE_BOX.isDefaultVMFlag("MaxVectorSize");
        vmInfo.append("MaxVectorSizeIsDefault:")
              .append(maxVectorSizeIsDefault ? 1 : 0)
              .append(System.lineSeparator());
        long loopMaxUnroll = WHITE_BOX.getIntxVMFlag("LoopMaxUnroll");
        vmInfo.append("LoopMaxUnroll:").append(loopMaxUnroll).append(System.lineSeparator());

        // CPU feature dependent info
        long useAVX = 0;
        boolean useAVXIsDefault = true;
        if (cpuFeatures.contains(" sse, ")) {
            useAVX = WHITE_BOX.getIntVMFlag("UseAVX");
            useAVXIsDefault = WHITE_BOX.isDefaultVMFlag("UseAVX");
        }
        vmInfo.append("UseAVX:").append(useAVX).append(System.lineSeparator());
        vmInfo.append("UseAVXIsDefault:")
              .append(useAVXIsDefault ? 1 : 0)
              .append(System.lineSeparator());

        vmInfo.append(END_VM_INFO);
        TestFrameworkSocket.write(vmInfo.toString(), "VMInfo");
    }
}
