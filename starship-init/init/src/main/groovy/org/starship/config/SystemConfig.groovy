/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package org.starship.config

/**
 * SystemConfig is responsible for configuring the system through a custom DSL.
 * It allows setting the hostname, defining mount points, spawning tasks, 
 * and applying all configurations.
 *
 * Example usage in DSL:
 *
 * <pre>
 * system {
 *     hostname "starship"
 *     mount "nfs", on: "/mnt/data"
 *     spawn "backup.sh", name: "dailyBackup"
 * }
 * </pre>
 *
 * Methods:
 * - `hostname(String name)`: Sets the system hostname.
 * - `mount(String type, Map options)`: Defines a mount point with the type and options.
 * - `spawn(String command, Map args)`: Spawns a task with the given command and arguments.
 * - `apply()`: Applies all the specified configurations with logging.
 *
 * Stubs are used in place of actual execution for this implementation.
 *
 * Logs are provided using the Slf4j annotation for better traceability.
 */
interface SystemConfig {

    /**
     * Mount a filesystem.
     * @param type the source of the mount (e.g., "proc", "sysfs")
     * @param path the mount point (e.g., "/proc", "/sys")
     * @param fsType the filesystem type (e.g., "proc", "sysfs")
     * @param flags optional mount flags
     * @param data optional data passed to the mount
     */
    void mount(String type, String path, String fsType, long flags, String data)

    /**
     * Unmount a filesystem at the specified path.
     * @param path the mount point path to unmount
     */
    void umount(String path)

    /**
     * Check if a given path is already mounted.
     * @param path the mount point path to check
     * @return true if mounted, false otherwise
     */
    boolean mountpoint(String path)

    /**
     * Create the /dev/console device, if it does not exist.
     */
    void makeConsole()
}