/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.tools.jdi;

import java.util.EventObject;

import com.sun.jdi.ThreadReference;

/*
 * The name "action" is used to avoid confusion with JDI events.
 */
class ThreadAction extends EventObject {

    private static final long serialVersionUID = 5690763191100515283L;

    // Event ids
    /*static final int THREAD_SUSPENDED = 1;*/
    static final int THREAD_RESUMABLE = 2;

    int id;

    ThreadAction(ThreadReference thread, int id) {
        super(thread);
        this.id = id;
    }

    ThreadReference thread() {
        return (ThreadReference)getSource();
    }

    int id() {
        return id;
    }
}
