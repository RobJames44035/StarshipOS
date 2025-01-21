/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.hotspot.tools.ctw;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * Handler for jar-files containing classes to compile.
 */
public class ClassPathJarEntry extends PathHandler.PathEntry {
    private final JarFile jarFile;

    public ClassPathJarEntry(Path root) {
        super(root);
        if (!Files.exists(root)) {
            throw new Error(root + " file not found");
        }
        try {
            jarFile = new JarFile(root.toFile());
        } catch (IOException e) {
            throw new Error("can not read " + root + " : " + e.getMessage(), e);
        }
    }

    @Override
    protected Stream<String> classes() {
        return jarFile.stream()
                      .map(JarEntry::getName)
                      .filter(Utils::isClassFile)
                      .map(Utils::fileNameToClassName);
    }

    @Override
    protected String description() {
        return "# jar: " + root;
    }

    @Override
    protected byte[] findByteCode(String classname) {
        try {
            String filename = Utils.classNameToFileName(classname);
            JarEntry entry = jarFile.getJarEntry(filename);

            if (entry == null) {
                return null;
            }

            try (InputStream is = jarFile.getInputStream(entry)) {
                return is.readAllBytes();
            }

        } catch (IOException e) {
            e.printStackTrace(CompileTheWorld.ERR);
            return null;
        }
    }
}

