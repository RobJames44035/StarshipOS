/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

import org.starship.service.ServiceRestartPolicy



init {
    system {
        setHostname "starship-os"

        // Define the /dev/console special device
        makeConsole()

        // Mount needed/required filesystems
        mount("proc", "/proc", "proc", 0L, null)
        mount("sysfs", "/sys", "sysfs", 0L, null)
        mount("devtmpfs", "/dev", "devtmpfs", 0L, null)
        mount("tmpfs", "/tmp", "tmpfs", 0L, null)
        mount("tmpfs", "/run", "tmpfs", 0L, null)

        // Spawn any required processes
//        spawn("/opt/apache-activemq-6.1.5/bin/activemq start", "ActiveMQ")

        services {
            service(
                    [
                            name        : "apache-activemq-6.1.5",
                            command     : "/opt/activemq/bin/activemq",
                            descr       : "Apache ActiveMQ is the most popular open source, multi-protocol, Java-based message broker.",
                            policy      : ServiceRestartPolicy.ALWAYS,
                            beforeStart : {}, // NOP
                            afterStart  : {}, // NOP
                            onFailure   : {}, // NOP
                            restartDelay: 0
                    ]
            )
        }
        startServices()
    }

    // Start a shell
    interactiveShell("Welcome! /bin/sh", "/bin/sh")
}
