/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach020;

import nsk.share.ClassUnloader;
import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach020Target extends TargetApplicationWaitingAgents {

    protected void targetApplicationActions() {
        log.display("Provoking garbage collection");
        ClassUnloader.eatMemory();
    }

    public static void main(String[] args) {
        new attach020Target().runTargetApplication(args);
    }
}
