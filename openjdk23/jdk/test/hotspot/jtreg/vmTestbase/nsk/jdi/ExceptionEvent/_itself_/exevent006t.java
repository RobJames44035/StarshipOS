/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package nsk.jdi.ExceptionEvent._itself_;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


public class exevent006t {
    public static void main(String args[]) throws ClassNotFoundException,
                                                  NoSuchMethodException,
                                                  InstantiationException,
                                                  IllegalAccessException,
                                                  InvocationTargetException {
        exevent006t _exevent006t = new exevent006t();
        System.exit(exevent006.JCK_STATUS_BASE + _exevent006t.communication(args));
    }

    int communication(String args[]) throws ClassNotFoundException,
                               NoSuchMethodException,
                               InstantiationException,
                               IllegalAccessException,
                               InvocationTargetException {
        String command;
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(exevent006.COMMAND_READY);
        command = pipe.readln();
        if (command.equals(exevent006.COMMAND_RUN)) {
            Class<?> testClass = Class.forName(exevent006.DEBUGGEE_CLASS+"Exception");
            Class<?> methodArgs[] = { String.class };
            Method testMeth = testClass.getMethod("exevent006traiseEx", methodArgs);
            Object testInstance = testClass.newInstance();
            Object parameters[] = { "oops!" };
            Object result = testMeth.invoke(testInstance, parameters);

            return exevent006.PASSED;
        } else
            return exevent006.FAILED;
    }
}

class exevent006tException {
    public void exevent006traiseEx(String arg) {
        int i = Integer.parseInt(arg);
    }
}
