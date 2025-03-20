/*
 * StarshipOS Copyright (c) 2025. R.A.James
 *
 * Licensed under GPL2, GPL3 and Apache 2
 */

#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>


// The code dynamically modifies environment variables, setting paths for shared libraries,
// the executable's PATH, and some required environment variables for a Java application.
// After setup, it attempts to execute the Java process with the configured environment.
int main() {
    const char *existing_ld_library_path = getenv("LD_LIBRARY_PATH");

    // Dynamically calculate and allocate enough space
    size_t path_length = strlen("/java/lib:/java/lib/server:/lib:/usr/lib") +
                         (existing_ld_library_path ? strlen(existing_ld_library_path) + 1 : 0) + 1;

    char *new_ld_library_path = malloc(path_length);
    if (new_ld_library_path == NULL) {
        fprintf(stderr, "Memory allocation failed for LD_LIBRARY_PATH: %s\n", strerror(errno));
        return EXIT_FAILURE;
    }

    // Construct LD_LIBRARY_PATH
    strcpy(new_ld_library_path, "/java/lib:/java/lib/server:/lib:/usr/lib");
    if (existing_ld_library_path) {
        strcat(new_ld_library_path, ":");
        strcat(new_ld_library_path, existing_ld_library_path);
    }

    // Set environment variables
    if (setenv("LD_LIBRARY_PATH", new_ld_library_path, 1) != 0) {
        perror("Failed to set LD_LIBRARY_PATH");
        free(new_ld_library_path);
        return EXIT_FAILURE;
    }
    free(new_ld_library_path);

    if (setenv("PATH", "/bin:/sbin:/usr/bin:/usr/sbin:/java/bin", 1) != 0 ||
        setenv("JAVA_HOME", "/java", 1) != 0 ||
        setenv("LANG", "C", 1) != 0 ||
        setenv("TERM", "linux", 1) != 0) {
        perror("Failed to set one or more environment variables");
        return EXIT_FAILURE;
    }

    // Debug output
//#ifdef DEBUG
    printf("LD_LIBRARY_PATH: %s\n", getenv("LD_LIBRARY_PATH"));
    printf("PATH: %s\n", getenv("PATH"));
//#endif

    const char *java_binary = "/java/bin/java";
    char *const java_args[] = {
        "java",
        "-jar",
        "/var/lib/starship/init.jar",
        NULL
    };

    // Check if the Java binary exists and is executable
    if (access(java_binary, X_OK) != 0) {
        fprintf(stderr, "Java binary '%s' missing or not executable: %s\n", java_binary, strerror(errno));
        return EXIT_FAILURE;
    }

    // Execute the Java process
    if (execv(java_binary, java_args) == -1) {
        perror("Failed to execute Java binary");
        return EXIT_FAILURE;
    }

    return EXIT_SUCCESS; // This should never be reached
}
