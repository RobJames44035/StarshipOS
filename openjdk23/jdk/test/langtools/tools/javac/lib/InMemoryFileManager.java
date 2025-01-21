/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.tools.*;

import static java.util.Collections.unmodifiableMap;

/**
 * class for storing source/byte code in memory.
 */
public class InMemoryFileManager extends ForwardingJavaFileManager {

    private final Map<String, InMemoryJavaFile> classes = new HashMap<>();

    public InMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {

        InMemoryJavaFile javaFile = new InMemoryJavaFile(className);
        classes.put(className, javaFile);
        return javaFile;
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return new ClassLoader(this.getClass().getClassLoader()) {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                InMemoryJavaFile classData = classes.get(name);
                if (classData == null) throw new ClassNotFoundException(name);
                byte[] byteCode = classData.bos.toByteArray();
                return defineClass(name, byteCode, 0, byteCode.length);
            }
        };
    }

    public Map<String, ? extends JavaFileObject> getClasses() {
        return unmodifiableMap(classes);
    }

    private static class InMemoryJavaFile extends SimpleJavaFileObject {

        private final ByteArrayOutputStream bos =
                new ByteArrayOutputStream();


        protected InMemoryJavaFile(String name) {
            super(URI.create("mfm:///" + name.replace('.', '/') + Kind.CLASS.extension), Kind.CLASS);
        }

        @Override
        public OutputStream openOutputStream() throws IOException {
            return bos;
        }

        @Override
        public InputStream openInputStream() throws IOException {
            return new ByteArrayInputStream(bos.toByteArray());
        }
    }
}
