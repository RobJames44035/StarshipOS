/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package nsk.jdwp.ReferenceType.SignatureWithGeneric;

import java.io.*;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

public class sigwithgeneric001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        Log log = new Log(System.err, argHandler);
        IOPipe pipe = argHandler.createDebugeeIOPipe(log);

        // load tested classes
        sigwithgeneric001b<String> _sigwithgeneric001b =
            new sigwithgeneric001b<String>();
        sigwithgeneric001c<Boolean, Integer> _sigwithgeneric001c =
            new sigwithgeneric001c<Boolean, Integer>();
        sigwithgeneric001e _sigwithgeneric001e =
            new sigwithgeneric001e();
        sigwithgeneric001if<Object> _sigwithgeneric001if =
            new sigwithgeneric001d<Object>();
        sigwithgeneric001f _sigwithgeneric001f =
            new sigwithgeneric001f();
        sigwithgeneric001g<sigwithgeneric001f> _sigwithgeneric001g =
            new sigwithgeneric001g<sigwithgeneric001f>();
        sigwithgeneric001h<Byte, Double, Float> _sigwithgeneric001h =
            new sigwithgeneric001h<Byte, Double, Float>();

        log.display("Debuggee VM started\nSending command: "
            + sigwithgeneric001.COMMAND_READY);
        pipe.println(sigwithgeneric001.COMMAND_READY);

        log.display("Waiting for command: "
            + sigwithgeneric001.COMMAND_QUIT + " ...");
        String cmd = pipe.readln();
        log.display(" ... Received command: " + cmd
            + "\nDebuggee is exiting ...");

        System.exit(Consts.JCK_STATUS_BASE + Consts.TEST_PASSED);
    }
}

/*
 * Dummy classes used only for verifying generic signature information
 * in a debugger.
 */

class sigwithgeneric001b<L extends String> {}

class sigwithgeneric001c<A, B extends Integer> {}

interface sigwithgeneric001if<I> {
    int sigwithgeneric001ifMeth();

    <I> int sigwithgeneric001ifMeth2(I v);
}

class sigwithgeneric001d<T> implements sigwithgeneric001if<T> {
    public int sigwithgeneric001ifMeth() {
        return 1;
    }

    public <T> int sigwithgeneric001ifMeth2(T v) {
        return 2;
    }
}

class sigwithgeneric001e {}

class sigwithgeneric001f extends sigwithgeneric001e implements sigwithgeneric001if {
    public int sigwithgeneric001ifMeth() {
        return 3;
    }

    public int sigwithgeneric001ifMeth2(Object v) {
        return 4;
    }
}

class sigwithgeneric001g<E extends sigwithgeneric001e & sigwithgeneric001if> {}

interface sigwithgeneric001if2<A, B, C> {}

class sigwithgeneric001h<A1, B1, C1>
    extends sigwithgeneric001d<A1>
    implements sigwithgeneric001if2<A1, B1, C1> {}
