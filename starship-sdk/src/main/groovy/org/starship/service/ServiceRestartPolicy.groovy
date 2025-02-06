package org.starship.service

enum ServiceRestartPolicy {
    NO("no", "The service will not be restarted automatically under any circumstances."),
    ON_SUCCESS("on-success", "The service will restart only if it exits cleanly with an exit code of `0` or `Success`."),
    ON_FAILURE("on-failure", "The service will restart if it exits with a non-zero exit code, is terminated by a signal, or due to a timeout."),
    ON_ABNORMAL("on-abnormal", "The service will restart only if it gets terminated by a signal (e.g., segmentation fault or kill signal)."),
    ON_ABORT("on-abort", "The service will restart if it is aborted (e.g., due to an assertion failure or explicit abort)."),
    ON_WATCHDOG("on-watchdog", "The service will restart if it fails to send the expected watchdog signal to Init.groovy."),
    ALWAYS("always", "The service will restart regardless of why it stopped.")

    final String name
    final String descr

    ServiceRestartPolicy(final String name, final String descr) {
        this.name = name
        this.descr = descr
    }

    static ServiceRestartPolicy fromCode(String name) {
        values().find { it.name == name }
    }

    static ServiceRestartPolicy fromName(String name) {
        values().find { it.name() == name.toUpperCase() }
    }
}

