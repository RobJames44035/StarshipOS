package org.starship.config

import groovy.util.logging.Slf4j

@Slf4j
class SpawnProcess {

    Process process

    Process spawn(String commandline) {
        try {
            String[] command = commandline.split(" ")
            ProcessBuilder processBuilder = new ProcessBuilder()
            processBuilder.command(command).start()

            // Read the output of the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))
            String line
            while ((line = reader.readLine()) != null) {
                log.info(line)
            }

            // Wait for the process to finish
            int exitCode = process.waitFor()
        } catch (IOException | InterruptedException e) {
            log.error("Failed spawning process." + e.message, e)
        }
    }
}
