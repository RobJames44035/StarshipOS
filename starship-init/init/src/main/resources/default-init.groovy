/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */

init {
    system {

        setHostname "starship-os"

        // Define the /dev/console special device
        makeConsole()

        // Mount needed/required filesystems
        mount("proc", "/proc", "proc", 0L, null)
        mount("tmpfs", "/run", "tmpfs", 0L, null)
        mount("sysfs", "/sys", "sysfs", 0L, null)
        mount("devtmpfs", "/dev", "devtmpfs", 0L, null)
        mount("tmpfs", "/tmp", "tmpfs", 0L, null)

        // OSGi bundle manager
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
//        }
    }
}
