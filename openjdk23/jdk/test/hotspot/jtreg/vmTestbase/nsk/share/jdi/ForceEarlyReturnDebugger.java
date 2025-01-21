/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */
package nsk.share.jdi;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

/*
 * Class contains methods common for nsk/jdi/ThreadReference/forceEarlyReturn tests
 */
public class ForceEarlyReturnDebugger extends TestDebuggerType2 {
    protected boolean canRunTest() {
        if (!vm.canForceEarlyReturn()) {
            log.display("TEST CANCELED due to:  vm.canForceEarlyReturn() = false");
            return false;
        } else
            return super.canRunTest();
    }

    protected void testMethodExitEvent(ThreadReference thread, String methodName) {
        testMethodExitEvent(thread, methodName, true);
    }

    /*
     * Method for checking is after forceEarlyReturn MethodExitEvent is generated as it would be in a normal return
     * Before calling this method forceEarlyReturn() should be already called and tested thread should be suspended
     */
    protected void testMethodExitEvent(ThreadReference thread, String methodName, boolean resumeThreadAfterEvent) {
        MethodExitRequest methodExitRequest;
        methodExitRequest = debuggee.getEventRequestManager().createMethodExitRequest();
        methodExitRequest.addThreadFilter(thread);
        methodExitRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        methodExitRequest.enable();

        EventListenerThread listenerThread = new EventListenerThread(methodExitRequest);
        listenerThread.start();
        listenerThread.waitStartListen();

        thread.resume();

        Event event = listenerThread.getEvent();

        if (event == null) {
            setSuccess(false);
            log.complain("MethodExitEvent was not generated " + ", method: " + methodName);
        } else {
            if (!((MethodExitEvent) event).method().name().equals(methodName)) {
                setSuccess(false);
                log.complain("Invalid MethodExitEvent: expected method - " + methodName + ", actually - "
                        + ((MethodExitEvent) event).method().name());
            }
        }

        methodExitRequest.disable();
        vm.eventRequestManager().deleteEventRequest(methodExitRequest);

        if (resumeThreadAfterEvent)
            thread.resume();
    }
}
