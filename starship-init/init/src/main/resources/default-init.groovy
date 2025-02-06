/*
 * StarshipOS Copyright (c) 2025. R. A. James
 */

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


        // Start a shell
    }
}
