/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.tools.jnativescan;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.JarFile;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

sealed interface ClassFileSource {
    String moduleName();
    Path path();

    Stream<byte[]> classFiles(Runtime.Version version) throws IOException;

    record Module(ModuleReference reference) implements ClassFileSource {
        @Override
        public String moduleName() {
            return reference.descriptor().name();
        }

        @Override
        public Path path() {
            URI location = reference.location().orElseThrow();
            return Path.of(location);
        }

        @Override
        public Stream<byte[]> classFiles(Runtime.Version version) throws IOException {
            ModuleReader reader = reference().open();
            return reader.list()
                .filter(resourceName -> resourceName.endsWith(".class"))
                .map(resourceName -> {
                    try (InputStream stream = reader.open(resourceName).orElseThrow()) {
                        return stream.readAllBytes();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }).onClose(() -> {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        }
    }

    record ClassPathJar(Path path) implements ClassFileSource {
        @Override
        public String moduleName() {
            return "ALL-UNNAMED";
        }

        @Override
        public Stream<byte[]> classFiles(Runtime.Version version) throws IOException {
            JarFile jf = new JarFile(path().toFile(), false, ZipFile.OPEN_READ, version);
            return jf.versionedStream()
                .filter(je -> je.getName().endsWith(".class"))
                .map(je -> {
                    try (InputStream stream = jf.getInputStream(je)){
                        return stream.readAllBytes();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }).onClose(() -> {
                    try {
                        jf.close();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        }
    }

    record ClassPathDirectory(Path path) implements ClassFileSource {
        @Override
        public String moduleName() {
            return "ALL-UNNAMED";
        }

        @Override
        public Stream<byte[]> classFiles(Runtime.Version version) throws IOException {
            return Files.walk(path)
                .filter(file -> Files.isRegularFile(file) && file.toString().endsWith(".class"))
                .map(file -> {
                    try (InputStream stream = Files.newInputStream(file)){
                        return stream.readAllBytes();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                });
        }
    }
}
