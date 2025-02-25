#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main() {
    // Retrieve existing LD_LIBRARY_PATH, if any
    const char *existing_ld_library_path = getenv("LD_LIBRARY_PATH");

    // Allocate enough memory to avoid overwriting existing values
    char new_ld_library_path[1024] = "/java/lib:/java/lib/server:/lib:/usr/lib";
    if (existing_ld_library_path != NULL) {
        // Append the new paths to the existing paths
        strncat(new_ld_library_path, ":", sizeof(new_ld_library_path) - strlen(new_ld_library_path) - 1);
        strncat(new_ld_library_path, existing_ld_library_path, sizeof(new_ld_library_path) - strlen(new_ld_library_path) - 1);
    }

    // Set the updated LD_LIBRARY_PATH
    setenv("LD_LIBRARY_PATH", new_ld_library_path, 1);

    // Set other environment variables
    setenv("PATH", "/bin:/sbin:/usr/bin:/usr/sbin:/java/bin", 1);
    setenv("JAVA_HOME", "/java", 1);
    setenv("LANG", "C", 1);
    setenv("TERM", "linux", 1);

    // Debugging information
    printf("LD_LIBRARY_PATH: %s\n", getenv("LD_LIBRARY_PATH"));
    printf("PATH: %s\n", getenv("PATH"));

    // Define the Java binary and arguments
    const char *java_binary = "/java/bin/java";
    char *const java_args[] = {
        "java",
        "-jar",
        "/var/lib/starship/init.jar",
        NULL
    };

    // Check if the Java binary exists and is executable
    if (access(java_binary, X_OK) != 0) {
        perror("Java binary not found or not executable");
        return EXIT_FAILURE;
    }

    // Attempt to execute the JVM process
    if (execv(java_binary, java_args) == -1) {
        perror("Failed to execute Java process");
    }

    // If execv() fails, fallback to an emergency shell
    fprintf(stderr
