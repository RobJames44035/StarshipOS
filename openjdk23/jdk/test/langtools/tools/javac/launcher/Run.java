/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.javac.launcher.SourceLauncher;
import com.sun.tools.javac.launcher.Result;

record Run(String stdOut, String stdErr, Throwable exception, Result result) {
    static Run of(Path file) {
        return Run.of(file, List.of(), List.of("1", "2", "3"));
    }

    static Run of(Path file, List<String> runtimeArgs, List<String> appArgs) {
        List<String> args = new ArrayList<>();
        args.add(file.toString());
        args.addAll(appArgs);

        PrintStream prev = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (PrintStream out = new PrintStream(baos, true)) {
            System.setOut(out);
            StringWriter sw = new StringWriter();
            try (PrintWriter err = new PrintWriter(sw, true)) {
                var launcher = new SourceLauncher(err);
                var result = launcher.run(runtimeArgs.toArray(String[]::new), args.toArray(String[]::new));
                return new Run(baos.toString(), sw.toString(), null, result);
            } catch (Throwable throwable) {
                return new Run(baos.toString(), sw.toString(), throwable, null);
            }
        } finally {
            System.setOut(prev);
        }
    }
}
