/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.internal.util;

import java.nio.file.Path;
import java.util.Optional;

public final class PathUtils {

    public static String getSuffix(Path path) {
        String filename = replaceSuffix(path.getFileName(), null).toString();
        return path.getFileName().toString().substring(filename.length());
    }

    public static Path addSuffix(Path path, String suffix) {
        Path parent = path.getParent();
        String filename = path.getFileName().toString() + suffix;
        return parent != null ? parent.resolve(filename) : Path.of(filename);
    }

    public static Path replaceSuffix(Path path, String suffix) {
        Path parent = path.getParent();
        String filename = path.getFileName().toString().replaceAll("\\.[^.]*$",
                "") + Optional.ofNullable(suffix).orElse("");
        return parent != null ? parent.resolve(filename) : Path.of(filename);
    }

    public static Path resolveNullablePath(Path base, Path path) {
        return Optional.ofNullable(path).map(base::resolve).orElse(null);
    }
}
