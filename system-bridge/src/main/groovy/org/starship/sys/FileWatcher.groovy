package org.starship.sys

import java.nio.file.*

class FileWatcher {
    static void watch(String directoryPath, Closure onChange) {
        try {
            // Get a WatchService for the filesystem
            WatchService watchService = FileSystems.default.newWatchService()
            Path path = Paths.get(directoryPath)

            // Register the directory to monitor for ENTRY_MODIFY (file modification)
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY)

            println "Watching directory: ${directoryPath} for changes..."

            while (true) {
                WatchKey key
                try {
                    // Wait for events (blocks until an event happens)
                    key = watchService.take()
                } catch (InterruptedException e) {
                    e.printStackTrace()
                    return
                }

                // Process events
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind()

                    // Get the filename
                    Path fileName = event.context() as Path
                    println "File ${fileName} was ${kind.name()}"

                    // Trigger onChange callback
                    onChange(fileName.toString())
                }

                // Reset the key to continue watching
                boolean valid = key.reset()
                if (!valid) {
                    println "WatchKey is no longer valid. Stopping watch."
                    break
                }
            }
        } catch (Exception e) {
            println "Error watching directory: ${e.message}"
            e.printStackTrace()
        }
    }
}
