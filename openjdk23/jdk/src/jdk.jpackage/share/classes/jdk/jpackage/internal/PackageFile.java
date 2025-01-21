/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package jdk.jpackage.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

final class PackageFile {

    /**
     * Returns path to package file.
     * @param appImageDir - path to application image
     */
    public static Path getPathInAppImage(Path appImageDir) {
        return ApplicationLayout.platformAppImage()
                .resolveAt(appImageDir)
                .appDirectory()
                .resolve(FILENAME);
    }

    PackageFile(String packageName) {
        Objects.requireNonNull(packageName);
        this.packageName = packageName;
    }

    void save(ApplicationLayout appLayout) throws IOException {
        Path dst = Optional.ofNullable(appLayout.appDirectory()).map(appDir -> {
            return appDir.resolve(FILENAME);
        }).orElse(null);

        if (dst != null) {
            Files.createDirectories(dst.getParent());
            Files.writeString(dst, packageName);
        }
    }

    private final String packageName;

    private static final String FILENAME = ".package";
}
