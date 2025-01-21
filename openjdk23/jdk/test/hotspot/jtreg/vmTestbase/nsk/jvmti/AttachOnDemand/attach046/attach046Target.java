/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach046;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach046Target extends TargetApplicationWaitingAgents {

    protected void targetApplicationActions() {
        String redefinedClassVersion = new ClassToRedefine().getVersion();
        log.display("Redefined class version: " + redefinedClassVersion);

        /*
         * Target application should see result of redefinition done by the
         * last loaded agent
         */
        final String expectedVersion = "2.0";

        if (!redefinedClassVersion.equals(expectedVersion)) {
            setStatusFailed("ClassToRedefine.getVersion() returned unexpected value" +
                        ", expected is '" + expectedVersion + "'");
        }
    }

    public static void main(String[] args) {
        new attach046Target().runTargetApplication(args);
    }
}
