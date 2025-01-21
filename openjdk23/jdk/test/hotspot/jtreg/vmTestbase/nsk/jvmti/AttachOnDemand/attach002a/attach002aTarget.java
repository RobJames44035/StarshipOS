/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach002a;

import nsk.share.aod.TargetApplicationWaitingAgents;

public class attach002aTarget extends TargetApplicationWaitingAgents {

    protected void targetApplicationActions() throws Throwable {
        /*
         * Provoke VMObjectAlloc event, InterruptedException class is redefined
         * in the handler for this event
         */
        InterruptedException e = InterruptedException.class.newInstance();

        // here redefined class version should be used
        e = new InterruptedException("Test InterruptedException");
        String message = e.getMessage();
        log.display("InterruptedException.getMessage(): '" + message + "'");

        final String expectedMessage = "attach002a: redefined version";

        if (!message.equals(expectedMessage)) {
            setStatusFailed("InterruptedException.getMessage() returns unexpected value " +
                        "(expected is '" + expectedMessage + "')" +
                        ", probably class wasn't redefined");
        }
    }

    public static void main(String[] args) {
        new attach002aTarget().runTargetApplication(args);
    }
}
