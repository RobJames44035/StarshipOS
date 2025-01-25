// Init.config: Groovy DSL for system initialization
system {
    hostname "starship-os"
    heartbeatTimeoutMs  5000
    maxRetryAttempts    5
    bundleManagerSocketPath "/tmp/bundlemanager.sock"
    bundleManagerCmd        "java -jar /var/lib/starship/bundlemanager.jar"
    primaryConfigPath       "/etc/starship/config.d/default.groovy"
    fallbackConfigPath      "resources/default-init.groovy"


    mount "proc", on: "/proc"
    mount "sys", on: "/sys"
    mount "tmpfs", on: "/dev/shm"

    log(level: "DEBUG", location: "/var/log/starship.log")


//     spawn "java -jar /usr/local/lib/my-java-service.jar", name: "JavaService"
//     spawn "dhclient", name: "NetworkManager"

//     service {
//         name "CustomDaemon"
//         restartPolicy "always"
//         command "java -jar /custom-daemon/daemon.jar"
//     }

    logging {
        level = "DEBUG"                   // Set the log level to DEBUG
        location = "/var/log/starship-os.log"  // Set the log file location
    }

    interactiveShell "Welcome to StarshipOS!"
}
