#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main() {
    // Retrieve the existing LD_LIBRARY_PATH, if any
    const char *existing_ld_library_path = getenv("LD_LIBRARY_PATH");

    // Allocate enough memory to avoid overwriting existing values
    char new_ld_library_path[1024] = "/java/lib:/java/lib/server:/lib:/usr/lib";
    if (existing_ld_library_path != NULL) {
        // Append the existing paths to the new paths
        strncat(new_ld_library_path, ":", sizeof(new_ld_library_path) - strlen(new_ld_library_path) - 1);
        strncat(new_ld_library_path, existing_ld_library_path, sizeof(new_ld_library_path) - strlen(new_ld_library_path) - 1);
    }

    // Set the updated LD_LIBRARY_PATH
    if (setenv("LD_LIBRARY_PATH", new_ld_library_path, 1) != 0) {
        perror("Failed to set LD_LIBRARY_PATH");
        exit(EXIT_FAILURE);
    }

    // Set other environment variables
    if (setenv("PATH", "/bin:/sbin:/usr/bin:/usr/sbin:/java/bin", 1) != 0) {
        perror("Failed to set PATH");
        exit(EXIT_FAILURE);
    }

    if (setenv("JAVA_HOME", "/java", 1) != 0) {
        perror("Failed to set JAVA_HOME");
        exit(EXIT_FAILURE);
    }

    if (setenv("LANG", "C", 1) != 0) {
        perror("Failed to set LANG");
        exit(EXIT_FAILURE);
    }

    if (setenv("TERM", "linux", 1) != 0) {
        perror("Failed to set TERM");
        exit(EXIT_FAILURE);
    }

    // Debugging information (can be removed in production)
    printf("LD_LIBRARY_PATH: %s\n", getenv("LD_LIBRARY_PATH"));
    printf("PATH: %s\n", getenv("PATH"));

    // Define the Java binary and arguments
    const char *java_binary = "/java/bin/java";
    char *const java_args[] = {
        "java",                      // Name of the program (used by JVM)
        "-jar",
        "/var/lib/starship/init.jar", // Path to the .jar file
        NULL
    };

    // Check if the Java binary exists and is executable
    if (access(java_binary, X_OK) != 0) {
        perror("Java binary not found or not executable");
        exit(EXIT_FAILURE); // Panic and terminate if Java binary is missing or not executable
    }

    // Attempt to execute the JVM process
    if (execv(java_binary, java_args) == -1) {
        perror("Failed to execute Java process");
        exit(EXIT_FAILURE); // Panic and terminate if execv fails
    }

    // Normally, the code should never reach this point
    return EXIT_FAILURE;
}
