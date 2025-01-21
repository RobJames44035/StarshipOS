/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @run main/othervm Zombies
 * @bug 6474073 6180151
 * @summary Make sure zombies don't get created on Unix
 * @author Martin Buchholz
 */

import java.io.*;

public class Zombies {

    static final String os = System.getProperty("os.name");

    static final String TrueCommand = os.contains("OS X")?
        "/usr/bin/true" : "/bin/true";

    public static void main(String[] args) throws Throwable {
        if (! new File("/usr/bin/perl").canExecute() ||
            ! new File("/bin/ps").canExecute())
            return;
        System.out.println("Looks like a Unix system.");
        long mypid = ProcessHandle.current().pid();
        System.out.printf("mypid: %d%n", mypid);

        final Runtime rt = Runtime.getRuntime();

        try {
            String[] cmd = {"no-such-file"};
            rt.exec(cmd);
            throw new Error("expected IOException not thrown");
        } catch (IOException expected) {/* OK */}

        try {
            String[] cmd = {"."};
            rt.exec(cmd);
            throw new Error("expected IOException not thrown");
        } catch (IOException expected) {/* OK */}

        try {
            String[] cmd = {TrueCommand};
            rt.exec(cmd, null, new File("no-such-dir"));
            throw new Error("expected IOException not thrown");
        } catch (IOException expected) {/* OK */}

        Process p = rt.exec(TrueCommand);
        ProcessHandle pp = p.toHandle().parent().orElse(null);
        System.out.printf("%s pid: %d, parent: %s%n", TrueCommand, p.pid(), pp);
        p.waitFor();

        // Count all the zombies that are children of this Java process
        final String[] zombieCounter = {
            "/usr/bin/perl", "-e",
                "$a=`/bin/ps -eo ppid,pid,s,command`;" +
                        "print @b=$a=~/^ *@{[getppid]} +[0-9]+ +Z.*$/mog;" +
                        "exit @b"
        };

        ProcessBuilder pb = new ProcessBuilder(zombieCounter);
        pb.inheritIO();
        int zombies = pb.start().waitFor();
        if (zombies != 0) {
            throw new Error(zombies + " zombies!");
        }
    }
}
