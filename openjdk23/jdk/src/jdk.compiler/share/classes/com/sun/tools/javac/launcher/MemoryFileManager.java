/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package com.sun.tools.javac.launcher;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardLocation;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;

/**
 * An in-memory file manager.
 *
 * <p>Class files (of kind {@link JavaFileObject.Kind#CLASS CLASS}) written to
 * {@link StandardLocation#CLASS_OUTPUT} will be written to an in-memory cache.
 * All other file manager operations will be delegated to a specified file manager.
 *
 * <p><strong>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</strong></p>
 */
final class MemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {
    private final Map<String, byte[]> map;

    MemoryFileManager(Map<String, byte[]> map, JavaFileManager delegate) {
        super(delegate);
        this.map = map;
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className,
                                               JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (location == StandardLocation.CLASS_OUTPUT && kind == JavaFileObject.Kind.CLASS) {
            return createInMemoryClassFile(className);
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }

    private JavaFileObject createInMemoryClassFile(String className) {
        URI uri = URI.create("memory:///" + className.replace('.', '/') + ".class");
        return new SimpleJavaFileObject(uri, JavaFileObject.Kind.CLASS) {
            @Override
            public OutputStream openOutputStream() {
                return new ByteArrayOutputStream() {
                    @Override
                    public void close() throws IOException {
                        super.close();
                        map.put(className, toByteArray());
                    }
                };
            }
        };
    }

    @Override
    public boolean contains(Location location, FileObject fo) throws IOException {
        return fo instanceof ProgramFileObject || super.contains(location, fo);
    }
}
