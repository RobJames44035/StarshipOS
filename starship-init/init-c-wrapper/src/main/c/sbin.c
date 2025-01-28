#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main() {
    const char *java_binary = "/bin/java";
    char *const java_args[] = {
        "java",
        "-Xmx2g",
        "-Xms1g",
        "-Xss32m",
        "-jar",
        "/var/lib/starship/init.jar",
        NULL
    };

    // Attempt to execute the JVM process
    if (execv(java_binary, java_args) == -1) {
        perror("Failed to execute Java process");
    }

    // If execv() fails, fallback to an emergency shell
    fprintf(stderr, "Dropping to emergency shell...\n");
    char *const shell_args[] = { "/bin/sh", NULL };
    execv("/bin/sh", shell_args);

    // If even the shell fails, exit with a failure code
    perror("Failed to launch emergency shell");
    return EXIT_FAILURE;
}
