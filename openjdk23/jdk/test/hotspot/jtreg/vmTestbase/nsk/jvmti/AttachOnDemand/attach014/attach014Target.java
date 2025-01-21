/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package nsk.jvmti.AttachOnDemand.attach014;

import nsk.share.aod.TargetApplicationWaitingAgents;

class ClassToLoad {

}

public class attach014Target extends TargetApplicationWaitingAgents {

    private static final String loadedClassName = "nsk.jvmti.AttachOnDemand.attach014.ClassToLoad";

    protected void targetApplicationActions() throws Throwable {
        log.display("Loading class '" + loadedClassName + "'");
        // provoke ClassLoad event
        Class.forName(loadedClassName, true, this.getClass().getClassLoader());
    }

    public static void main(String[] args) {
        new attach014Target().runTargetApplication(args);
    }
}
