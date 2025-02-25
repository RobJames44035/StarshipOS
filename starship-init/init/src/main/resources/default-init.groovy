

//import org.starship.service.ServiceRestartPolicy


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

        // Spawn any required processes
//        spawn(command: "", name: "")

//        services {
//            // Message Broker
//            service(
//                    [
//                            name        : "apache-activemq-6.1.5",
//                            command     : "/opt/activemq/bin/activemq start",
//                            descr       : "Apache ActiveMQ is an open source, multi-protocol, Java-based message broker.",
//                            policy      : ServiceRestartPolicy.ALWAYS,
//                            beforeStart : {}, // NOP
//                            afterStart  : {}, // NOP
//                            onFailure   : {}, // NOP
//                            restartDelay: 10
//                    ]
//            )
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
