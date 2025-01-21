/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach021;

import nsk.share.ClassUnloader;
import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach021Target extends TargetApplicationWaitingAgents {

    /*
     * native methods should be registered by the test agent
     */
    private static native boolean setTagFor(Object obj);

    private static native void shutdownAgent();

    private boolean createTaggedObject() {
        Object object = new Object();

        log.display("Setting tag for " + object);

        if (!setTagFor(object)) {
            setStatusFailed("Error during object tagging");
            return false;
        }

        return true;
    }

    protected void targetApplicationActions() throws Throwable {
        try {
            if (createTaggedObject()) {
                log.display("Provoking GC");
                ClassUnloader.eatMemory();
            }
        } finally {
            shutdownAgent();
        }
    }

    public static void main(final String[] args) {
        new attach021Target().runTargetApplication(args);
    }
}
