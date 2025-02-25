/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */
//file:noinspection unused

package org.starship.sys

import sun.misc.Signal

/**
 * Enum representing various Linux signal numbers and their meanings.
 *
 * Each signal is defined with its code (Linux signal number)
 * and a brief description of what the signal means.
 */
enum SignalEnum {
    SIGHUP(1, "Terminal hang-up"),
    SIGINT(2, "Interrupt from keyboard (Ctrl+C)"),
    SIGQUIT(3, "Quit from keyboard"),
    SIGILL(4, "Illegal instruction"),
    SIGTRAP(5, "Trace/breakpoint trap"),
    SIGABRT(6, "Abort signal"),
    SIGBUS(7, "Bus error"),
    SIGFPE(8, "Floating point exception"),
    SIGKILL(9, "Kill signal (non-catchable)"),
    SIGUSR1(10, "User-defined signal 1"),
    SIGSEGV(11, "Invalid memory reference"),
    SIGUSR2(12, "User-defined signal 2"),
    SIGPIPE(13, "Broken pipe"),
    SIGALRM(14, "Alarm clock"),
    SIGTERM(15, "Termination signal"),
    SIGCHLD(17, "Child process status change"),
    SIGCONT(18, "Continue execution"),
    SIGSTOP(19, "Stop process (non-catchable)"),
    SIGTSTP(20, "Stop typed at terminal"),
    SIGTTIN(21, "Background process attempting input"),
    SIGTTOU(22, "Background process attempting output");

    final int code        // Linux signal number
    final String meaning  // Signal description

    /**
    * Constructs a SignalEnum instance with the specified signal code and its description.
    *
    * @param code the Linux signal number.
    * @param meaning the description of the signal.
    */
    SignalEnum(int code, String meaning) {
        this.code = code
        this.meaning = meaning
    }

    /**
    * Retrieves the SignalEnum constant by its signal code.
    *
    * @param code the Linux signal number to look for.
    * @return the SignalEnum representing the signal with the specified code,
    *         or {@code null} if no such signal exists.
    */
    static SignalEnum fromCode(int code) {
        values().find { it.code == code }
    }

    /**
    * Retrieves the SignalEnum constant by its name.
    *
    * The search is case-insensitive.
    *
    * @param name the name of the signal to look for.
    * @return the SignalEnum representing the signal with the specified name,
    *         or {@code null} if no such signal exists.
    */
    static SignalEnum fromName(String name) {
        values().find { it.name() == name.toUpperCase() }
    }
}
