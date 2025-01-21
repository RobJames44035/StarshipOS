/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
package jdk.internal.util;

import jdk.internal.misc.VM;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HexFormat;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassFile dumper utility class to log normal and hidden classes.
 *
 * @implNote
 * Because this class is called by MethodHandleStatics, LambdaForms generation
 * and LambdaMetafactory, make use of lambda lead to recursive calls cause stack overflow.
 */
public final class ClassFileDumper {
    private static final ConcurrentHashMap<String, ClassFileDumper> DUMPER_MAP
            = new ConcurrentHashMap<>();

    /**
     * Returns a ClassFileDumper instance for the given key with a given
     * dump path. To enable dumping of the generated classes
     *     -D<key> or -D<key>=true
     *
     * The system property is read only once when it is the first time
     * the dumper instance for the given key is created.
     */
    public static ClassFileDumper getInstance(String key, String path) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(path);

        var dumper = DUMPER_MAP.get(key);
        if (dumper == null) {
            var newDumper = new ClassFileDumper(key, path);
            var v = DUMPER_MAP.putIfAbsent(key, newDumper);
            dumper = v != null ? v : newDumper;
        }

        if (dumper.isEnabled() && !path.equals(dumper.dumpDir)) {
            throw new IllegalArgumentException("mismatched dump path for " + key);
        }
        return dumper;
    }

    private final String key;
    private final String dumpDir;
    private final boolean enabled;
    private final AtomicInteger counter = new AtomicInteger();

    private ClassFileDumper(String key, String path) {
        String value = VM.getSavedProperty(key);
        this.key = key;
        boolean enabled = value != null && value.isEmpty() ? true : Boolean.parseBoolean(value);
        if (enabled) {
            validateDumpDir(path);
        }
        this.dumpDir = path;
        this.enabled = enabled;
    }

    public String key() {
        return key;
    }
    public boolean isEnabled() {
        return enabled;
    }

    private Path pathname(String name) {
        return Path.of(dumpDir, encodeForFilename(name) + ".class");
    }

    /**
     * This method determines the path name from the given name and {@code Class}
     * object.  If it is a hidden class, it will dump the given bytes at
     * a path of the given name with a suffix "." concatenated
     * with the suffix of the hidden class name.
     */
    public void dumpClass(String name, Class<?> c, byte[] bytes) {
        if (!isEnabled()) return;

        String cn = c.getName();
        int suffixIdx = cn.lastIndexOf('/');
        if (suffixIdx > 0) {
            name += '.' + cn.substring(suffixIdx + 1);
        }
        write(pathname(name), bytes);
    }

    /**
     * This method dumps the given bytes at a path of the given name with
     * a suffix ".failed-$COUNTER" where $COUNTER will be incremented
     * for each time this method is called.
     */
    public void dumpFailedClass(String name, byte[] bytes) {
        if (!isEnabled()) return;

        write(pathname(name + ".failed-" + counter.incrementAndGet()), bytes);
    }

    private void write(Path path, byte[] bytes) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, bytes);
        } catch (Exception ex) {
            if (VM.isModuleSystemInited()) {
                // log only when lambda is ready to use
                System.getLogger(ClassFileDumper.class.getName())
                        .log(System.Logger.Level.WARNING, "Exception writing to " +
                                path + " " + ex.getMessage());
            }
            // simply don't care if this operation failed
        }
    }

    /*
     * Validate if the given dir is a writeable directory if exists.
     */
    private static Path validateDumpDir(String dir) {
        Path path = Path.of(dir);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Fail to create " + path, ex);
            }
        }
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path " + path + " is not a directory");
        } else if (!Files.isWritable(path)) {
            throw new IllegalArgumentException("Directory " + path + " is not writable");
        }
        return path;
    }

    private static final HexFormat HEX = HexFormat.of().withUpperCase();
    private static final Set<Character> BAD_CHARS = Set.of('\\', ':', '*', '?', '"', '<', '>', '|');

    private static String encodeForFilename(String className) {
        int len = className.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = className.charAt(i);
            // control characters
            if (c <= 31 || BAD_CHARS.contains(c)) {
                sb.append('%');
                HEX.toHexDigits(sb, (byte)c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
