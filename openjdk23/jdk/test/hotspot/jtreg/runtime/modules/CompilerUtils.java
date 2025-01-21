/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class consists exclusively of static utility methods for invoking the
 * java compiler.
 *
 * This class will eventually move to jdk.testlibrary.
 */

public final class CompilerUtils {
    private CompilerUtils() { }

    /**
     * Compile all the java sources in {@code <source>/**} to
     * {@code <destination>/**}. The destination directory will be created if
     * it doesn't exist.
     *
     * All warnings/errors emitted by the compiler are output to System.out/err.
     *
     * @return true if the compilation is successful
     *
     * @throws IOException if there is an I/O error scanning the source tree or
     *                     creating the destination directory
     */
    public static boolean compile(Path source, Path destination, String ... options)
        throws IOException
    {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager jfm = compiler.getStandardFileManager(null, null, null);

        List<Path> sources
            = Files.find(source, Integer.MAX_VALUE,
                (file, attrs) -> (file.toString().endsWith(".java")))
                .collect(Collectors.toList());

        Files.createDirectories(destination);
        jfm.setLocationFromPaths(StandardLocation.CLASS_OUTPUT,
                                 Arrays.asList(destination));

        List<String> opts = Arrays.asList(options);
        JavaCompiler.CompilationTask task
            = compiler.getTask(null, jfm, null, opts, null,
                jfm.getJavaFileObjectsFromPaths(sources));

        return task.call();
    }
}
