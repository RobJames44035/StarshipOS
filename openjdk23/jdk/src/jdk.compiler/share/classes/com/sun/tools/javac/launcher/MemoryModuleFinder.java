/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package com.sun.tools.javac.launcher;

import java.io.ByteArrayInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import jdk.internal.module.Resources;

/**
 * An in-memory module finder, that uses an in-memory cache of classes written by
 * {@link MemoryFileManager}.
 *
 * <p><strong>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</strong></p>
 */
record MemoryModuleFinder(Map<String, byte[]> classes,
                          ModuleDescriptor descriptor,
                          ProgramDescriptor programDescriptor) implements ModuleFinder {
    @Override
    public Optional<ModuleReference> find(String name) {
        if (name.equals(descriptor.name())) {
            return Optional.of(new MemoryModuleReference());
        }
        return Optional.empty();
    }

    @Override
    public Set<ModuleReference> findAll() {
        return Set.of(new MemoryModuleReference());
    }

    class MemoryModuleReference extends ModuleReference {
        protected MemoryModuleReference() {
            super(descriptor, URI.create("memory:///" + descriptor.toNameAndVersion()));
        }

        @Override
        public ModuleReader open() {
            return new MemoryModuleReader();
        }
    }

    // Implementation based on jdk.internal.module.ModuleReferences#ExplodedModuleReader
    class MemoryModuleReader implements ModuleReader {
        private volatile boolean closed;

        private void ensureOpen() throws IOException {
            if (closed) {
                throw new IOException("ModuleReader is closed");
            }
        }

        public Optional<URI> find(String name) throws IOException {
            ensureOpen();
            // Try to find an in-memory compiled class first
            if (classes.get(name) != null) {
                return Optional.of(URI.create("memory:///" + name.replace('.', '/') + ".class"));
            }
            // Try to find file resource from root path next
            Path path = Resources.toFilePath(programDescriptor.sourceRootPath(), name);
            if (path != null) {
                try {
                    return Optional.of(path.toUri());
                } catch (IOError error) {
                    throw (IOException) error.getCause();
                }
            } else {
                return Optional.empty();
            }
        }

        public Optional<InputStream> open(String name) throws IOException {
            ensureOpen();
            // Try to find an in-memory compiled class first
            byte[] bytes = classes.get(name);
            if (bytes != null) {
                return Optional.of(new ByteArrayInputStream(bytes));
            }
            // Try to find file resource from root path next
            Path path = Resources.toFilePath(programDescriptor.sourceRootPath(), name);
            return path != null ? Optional.of(Files.newInputStream(path)) : Optional.empty();
        }

        public Optional<ByteBuffer> read(String name) throws IOException {
            ensureOpen();
            // Try to find an in-memory compiled class first
            byte[] bytes = classes.get(name);
            if (bytes != null) {
                return Optional.of(ByteBuffer.wrap(bytes));
            }
            // Try to find file resource from root path next
            Path path = Resources.toFilePath(programDescriptor.sourceRootPath(), name);
            return path != null ? Optional.of(ByteBuffer.wrap(Files.readAllBytes(path))) : Optional.empty();
        }

        public Stream<String> list() throws IOException {
            ensureOpen();
            var root = programDescriptor.sourceRootPath();
            var list = new ArrayList<String>();
            classes.keySet().stream().map(name -> name.replace('.', '/') + ".class").forEach(list::add);
            try (var stream = Files.walk(root, Integer.MAX_VALUE, new FileVisitOption[0])) {
                  stream
                    .map(file -> Resources.toResourceName(root, file))
                    .filter(name -> !name.isEmpty())
                    .forEach(list::add);
            }
            Collections.sort(list);
            return list.stream();
        }

        public void close() {
            this.closed = true;
        }
    }
}
