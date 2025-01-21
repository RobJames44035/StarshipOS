/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package jdk.jfr.internal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import jdk.jfr.internal.management.ChunkFilename;
import jdk.jfr.internal.util.ValueFormatter;
import jdk.jfr.internal.util.DirectoryCleaner;
import jdk.jfr.internal.util.Utils;

public final class Repository {

    private static final Path JAVA_IO_TMPDIR = Utils.getPathInProperty("java.io.tmpdir", null);
    private static final int MAX_REPO_CREATION_RETRIES = 1000;
    private static final Repository instance = new Repository();
    private static final String JFR_REPOSITORY_LOCATION_PROPERTY = "jdk.jfr.repository";
    private final Set<Path> cleanupDirectories = new HashSet<>();
    private Path baseLocation;
    private Path repository;
    private ChunkFilename chunkFilename;

    private Repository() {
    }

    public static Repository getRepository() {
        return instance;
    }

    public synchronized void setBasePath(Path baseLocation) throws IOException {
        if(baseLocation.equals(this.baseLocation)) {
            Logger.log(LogTag.JFR, LogLevel.INFO, "Same base repository path " + baseLocation.toString() + " is set");
            return;
        }
        // Probe to see if repository can be created, needed for fail fast
        // during JVM startup or JFR.configure
        this.repository = createRepository(baseLocation);
        this.chunkFilename = null;
        try {
            // Remove so we don't "leak" repositories, if JFR is never started
            // and shutdown hook not added.
            Files.delete(repository);
        } catch (IOException ioe) {
            Logger.log(LogTag.JFR, LogLevel.INFO, "Could not delete disk repository " + repository);
        }
        this.baseLocation = baseLocation;
    }

    public synchronized void ensureRepository() throws IOException {
        if (baseLocation == null) {
            setBasePath(JAVA_IO_TMPDIR);
        }
    }

    synchronized RepositoryChunk newChunk() {
        LocalDateTime timestamp = timestamp();
        try {
            if (!Files.exists(repository)) {
                this.repository = createRepository(baseLocation);
                JVM.setRepositoryLocation(repository.toString());
                System.setProperty(JFR_REPOSITORY_LOCATION_PROPERTY, repository.toString());
                cleanupDirectories.add(repository);
                chunkFilename = null;
            }
            if (chunkFilename == null) {
                chunkFilename = new ChunkFilename(repository);
            }
            String filename = chunkFilename.next(timestamp);
            return new RepositoryChunk(Path.of(filename));
        } catch (Exception e) {
            String errorMsg = String.format("Could not create chunk in repository %s, %s: %s", repository, e.getClass(), e.getMessage());
            Logger.log(LogTag.JFR, LogLevel.ERROR, errorMsg);
            JVM.abort(errorMsg);
            throw new InternalError("Could not abort after JFR disk creation error");
        }
    }

    private static LocalDateTime timestamp() {
        try {
            return LocalDateTime.now();
        } catch (DateTimeException d) {
            Logger.log(LogTag.JFR, LogLevel.INFO, "Could not create LocalDateTime with the default time zone. Using UTC time zone for chunk filename.");
            return LocalDateTime.now(ZoneOffset.UTC);
        }
    }

    private static Path createRepository(Path basePath) throws IOException {
        Path canonicalBaseRepositoryPath = createRealBasePath(basePath);
        Path f = null;

        String basename = ValueFormatter.formatDateTime(timestamp()) + "_" + JVM.getPid();
        String name = basename;

        int i = 0;
        for (; i < MAX_REPO_CREATION_RETRIES; i++) {
            f = canonicalBaseRepositoryPath.resolve(name);
            if (tryToUseAsRepository(f)) {
                break;
            }
            name = basename + "_" + i;
        }

        if (i == MAX_REPO_CREATION_RETRIES) {
            throw new IOException("Unable to create JFR repository directory using base location (" + basePath + ")");
        }
        return f.toRealPath();
    }

    private static Path createRealBasePath(Path path) throws IOException {
        if (Files.exists(path)) {
            if (!Files.isWritable(path)) {
                throw new IOException("JFR repository directory (" + path.toString() + ") exists, but isn't writable");
            }
            return path.toRealPath();
        }
        return Files.createDirectories(path).toRealPath();
    }

    private static boolean tryToUseAsRepository(Path path) {
        Path parent = path.getParent();
        if (parent == null) {
            return false;
        }
        try {
            Files.createDirectories(path);
        } catch (Exception e) {
            // file already existed or some other problem occurred
        }
        if (!Files.exists(path)) {
            return false;
        }
        if (!Files.isDirectory(path)) {
            return false;
        }
        return true;
    }

    synchronized void clear() {
        if (Options.getPreserveRepository()) {
            return;
        }

        for (Path p : cleanupDirectories) {
            try {
                DirectoryCleaner.clear(p);
                Logger.log(LogTag.JFR, LogLevel.INFO, "Removed repository " + p);
            } catch (IOException e) {
                Logger.log(LogTag.JFR, LogLevel.INFO, "Repository " + p + " could not be removed at shutdown: " + e.getMessage());
            }
        }
    }

    public synchronized Path getRepositoryPath() {
        return repository;
    }

    public synchronized Path getBaseLocation() {
        return baseLocation;
    }

}
