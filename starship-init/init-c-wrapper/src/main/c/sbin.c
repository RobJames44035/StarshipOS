#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

int main() {
    const char *java_binary = "/jdk/bin/java";
    char *const java_args[] = {
        "java",
//        "-Xcheck:jni",
//        "-XX:+UnlockDiagnosticVMOptions",
//        "-XX:+ShowMessageBoxOnError",
//        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005",
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
    char *const shell_args[] = {
        "sulogin",
        NULL
    };
    execv("/bin/busybox", shell_args);

    // If even the shell fails, exit with a failure code
    perror("Failed to launch emergency shell");
    return EXIT_FAILURE;
}
