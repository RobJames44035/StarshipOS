/*
 * Copyright (c) 2001, 2024, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package nsk.jdi.WatchpointRequest.addClassFilter_rt;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.*;
import java.io.*;

/**
 * The test for the implementation of an object of the type
 * WatchpointRequest.
 *
 * The test checks that results of the method
 * <code>com.sun.jdi.WatchpointRequest.addClassFilter(ReferenceType)</code>
 * complies with its spec.
 *
 * The test checks up on the following assertion:
 *    Restricts the events generated by this request to those
 *    whose location is in a class
 *    whose name matches a restricted regular expression.
 * The WatchpointRequest to test is AccessWatchpointRequest.
 * The cases to test include re-invocations of the method
 * addClassFilter() on the same AccessWatchpointtRequest object.
 * There are two AccessWatchpointRequests to check as follows:
 * (1) For AccessWatchpointRequest2, both invocations are with different
 *     ReferenceTypes restricting one AccessWatchpoint event to two classes.
 *     The test expects no AccessWatchpoint event will be received.
 * (2) For AccessWatchpointRequest1, both invocations are with the same
 *     ReferenceType restricting one AccessWatchpoint event to one class.
 *     The test expects this AccessWatchpoint event will be received.
 *
 * The test works as follows.
 * - The debugger resumes the debuggee and waits for the BreakpointEvent.
 * - The debuggee creates objects of needed classes
 *   and invokes the methodForCommunication to be suspended and
 *   to inform the debugger with the event.
 * - Upon getting the BreakpointEvent, the debugger
 *   - gets ReferenceTypes 1&2 for the Classes to filter,
 *   - sets up two AccessWatchpointRequests 1&2,
 *   - double restricts AccessWatchpointRequest1 to the RefTypes 1 and 1,
 *   - double restricts AccessWatchpointRequest2 to the RefTypes 1 and 2,
 *   - resumes debuggee's main thread, and
 *   - waits for the event.
 * - The debuggee creates and starts two threads, thread1 and thread2,
 *   generating the events to be filtered.
 * - Upon getting the events, the debugger performs checks required.
 */

public class filter_rt005 extends TestDebuggerType1 {

    public static void main (String argv[]) {
        int result = run(argv,System.out);
        if (result != 0) {
            throw new RuntimeException("TEST FAILED with result " + result);
        }
    }

    public static int run (String argv[], PrintStream out) {
        debuggeeName = "nsk.jdi.WatchpointRequest.addClassFilter_rt.filter_rt005a";
        return new filter_rt005().runThis(argv, out);
    }

    //  ************************************************    test parameters

    String className10 = "nsk.jdi.WatchpointRequest.addClassFilter_rt.filter_rt005aTestClass10";
    String className20 = "nsk.jdi.WatchpointRequest.addClassFilter_rt.filter_rt005aTestClass20";

    String className11 = "nsk.jdi.WatchpointRequest.addClassFilter_rt.filter_rt005aTestClass11";
    String className21 = "nsk.jdi.WatchpointRequest.addClassFilter_rt.filter_rt005aTestClass21";

    protected void testRun() {

        if ( !vm.canWatchFieldAccess() ) {
            display("......vm.canWatchFieldAccess == false :: test cancelled");
            vm.exit(Consts.JCK_STATUS_BASE);
            return;
        }

        EventRequest  eventRequest1 = null;
        EventRequest  eventRequest2 = null;

        String        property1     = "AccessWatchpointRequest1";
        String        property2     = "AccessWatchpointRequest2";

        ThreadReference thread1 = null;
        ThreadReference thread2 = null;

        String thread1Name = "thread1";
        String thread2Name = "thread2";

        String fieldName1 = "var101";
        String fieldName2 = "var201";

        ReferenceType testClassReference10 = null;
        ReferenceType testClassReference11 = null;
        ReferenceType testClassReference20 = null;
        ReferenceType testClassReference21 = null;

        Event newEvent = null;

        for (int i = 0; ; i++) {

            if (!shouldRunAfterBreakpoint()) {
                vm.resume();
                break;
            }

            display(":::::: case: # " + i);

            switch (i) {
                case 0:
                testClassReference10 =
                     (ReferenceType) vm.classesByName(className10).get(0);
                testClassReference11 =
                     (ReferenceType) vm.classesByName(className11).get(0);
                testClassReference20 =
                     (ReferenceType) vm.classesByName(className20).get(0);
                testClassReference21 =
                     (ReferenceType) vm.classesByName(className21).get(0);

                eventRequest1 = setting21AccessWatchpointRequest (null,
                                       testClassReference10, fieldName1,
                                       EventRequest.SUSPEND_ALL, property1);

                eventRequest2 = setting21AccessWatchpointRequest (null,
                                       testClassReference20, fieldName2,
                                       EventRequest.SUSPEND_ALL, property2);


                ((AccessWatchpointRequest) eventRequest1).addClassFilter(testClassReference11);
                ((AccessWatchpointRequest) eventRequest1).addClassFilter(testClassReference11);

                ((AccessWatchpointRequest) eventRequest2).addClassFilter(testClassReference11);
                ((AccessWatchpointRequest) eventRequest2).addClassFilter(testClassReference21);

                display("......waiting for AccessWatchpointEvent in tested thread");
                newEvent = eventHandler.waitForRequestedEvent(new EventRequest[]{eventRequest1, eventRequest2}, waitTime, true);

                if ( !(newEvent instanceof AccessWatchpointEvent)) {
                    setFailedStatus("New event is not AccessWatchpointEvent");
                } else {
                    String property = (String) newEvent.request().getProperty("number");
                    display("       got new AccessWatchpointEvent with property 'number' == " + property);

                    if ( !property.equals(property1) ) {
                        setFailedStatus("ERROR: property is not : " + property1);
                    }
                }
                vm.resume();
                break;

                default:
                throw new Failure("** default case 1 **");
            }
        }
        return;
    }

    private AccessWatchpointRequest setting21AccessWatchpointRequest (
                                                  ThreadReference thread,
                                                  ReferenceType   fieldClass,
                                                  String          fieldName,
                                                  int             suspendPolicy,
                                                  String          property        )
            throws Failure {
        try {
            display("......setting up AccessWatchpointRequest:");
            display("       thread: " + thread + "; fieldClass: " + fieldClass + "; fieldName: " + fieldName);
            Field field = fieldClass.fieldByName(fieldName);

            AccessWatchpointRequest
            awr = eventRManager.createAccessWatchpointRequest(field);
            awr.putProperty("number", property);

            if (thread != null)
                awr.addThreadFilter(thread);
            awr.setSuspendPolicy(suspendPolicy);

            display("      AccessWatchpointRequest has been set up");
            return awr;
        } catch ( Exception e ) {
            throw new Failure("** FAILURE to set up AccessWatchpointRequest **");
        }
    }

}