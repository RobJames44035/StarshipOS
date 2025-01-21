/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.aod.VirtualMachine.VirtualMachine09;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class VM09Target {

    public static void main(String[] args) {
        System.loadLibrary("VirtualMachine09agent00");
        System.out.println("Agent library was loaded");

        new TargetApplicationWaitingAgents().runTargetApplication(args);
    }
}
