/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */

init {
    system {

        // Export system variables
        exportEnvironment("JAVA_HOME", "/java")
        exportEnvironment("PATH", getEnvironmentalVariable("PATH") + ":/java/bin")

        setHostname "starship-os"

        // Define the /dev/console special device
        makeConsole()

        // Mount needed/required filesystems
        mount("proc", "/proc", "proc", 0L, null)
        mount("tmpfs", "/run", "tmpfs", 0L, null)
        mount("sysfs", "/sys", "sysfs", 0L, null)
        mount("devtmpfs", "/dev", "devtmpfs", 0L, null)
        mount("tmpfs", "/tmp", "tmpfs", 0L, null)

        startStopDaemon.start("syslogd", "start")

        // Spawn any required processes
//        spawn(command: "", name: "")

//        services {

        service(
                name: "syslogd",
                command: "/sbin/syslogd -n",
                descr: "syslog daemon",
                policy: ServiceRestartPolicy.ALWAYS,
                beforeStart: {},
                afterStart: {},
                onFailure: {},
                restartDelay: 10 // 10 seconds before restarting on failure
        )

//            // OSGi bundle manager
////            service(
////                    [
////                            name        : "osgi-manager-1.0.0",
////                            command     : "/usr/bin/java -jar /var/lib/starship/osgi-manager.jar",
////                            descr       : "StarshipOS OSGiManager service",
////                            policy      : ServiceRestartPolicy.ALWAYS,
////                            beforeStart : {}, // NOP
////                            afterStart  : {}, // NOP
////                            onFailure   : {}, // NOP
////                            restartDelay: 5
////                    ]
////            )
////            startServices()
//        }
    }
    interactiveShell("Welcome to StarshipOS, enjoy your flight!", "/bin/sh")
}
