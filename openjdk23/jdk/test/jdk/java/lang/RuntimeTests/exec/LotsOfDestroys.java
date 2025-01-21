/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/**
 * @test
 * @bug 4637504 4653814
 * @summary Destroy should close stderr, stdout and stdin
 * @author kladko
 */

public class LotsOfDestroys {
    static final int RUNS = 400;

    public static void main(String[] args) throws Exception {
        if (! UnixCommands.isUnix) {
            System.out.println("For UNIX only");
            return;
        }
        UnixCommands.ensureCommandsAvailable("echo");

        for (int i = 0; i <= RUNS; i++) {
            Process process = Runtime.getRuntime().exec(
                    UnixCommands.echo() + " x");
            process.destroy();
        }
    }
}
