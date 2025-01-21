/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.hotspot.tools.ctw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Handler for dirs containing jar-files with classes to compile.
 */
public class ClassPathJarInDirEntry {
    public static List<PathHandler> create(Path path) {
        Objects.requireNonNull(path);
        if (!Files.exists(path)) {
            throw new Error(path + " directory not found");
        }
        try {
            return Stream.concat(
                    Stream.of(new PathHandler(new JarInDirEntry(path))),
                    Files.list(path)
                         .filter(p -> p.getFileName().toString().endsWith(".jar"))
                         .map(ClassPathJarEntry::new)
                         .map(PathHandler::new))
                         .collect(Collectors.toList());
        } catch (IOException e) {
            throw new Error("can not read " + path + " directory : " + e.getMessage(), e);
        }
    }

    // dummy path handler, used just to print description before real handlers.
    private static class JarInDirEntry extends PathHandler.PathEntry {
        private JarInDirEntry(Path root) {
            super(root);
        }

        @Override
        protected byte[] findByteCode(String name) {
            return null;
        }

        @Override
        protected Stream<String> classes() {
            return Stream.empty();
        }

        @Override
        protected String description() {
            return "# jar_in_dir: " + root;
        }
    }
}

