/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class CustomLoader extends ClassLoader {
    private static PrintStream out = System.out;
    public  static ClassLoader myself;
    public  static ClassLoader agentClassLoader;
    public  static boolean failed = true;

    public CustomLoader(ClassLoader classLoader) {
        super(classLoader);
        myself = this;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        out.println("CustomLoader: loading class: " + name);
        if (name.equals("Agent")) {
            Class c = null;
            try {
                byte[] buf = locateBytes();
                c = defineClass(name, buf, 0, buf.length);
            } catch (IOException ex) {
                throw new ClassNotFoundException(ex.getMessage());
            }
            resolveClass(c);
            out.println("CustomLoader.loadClass after resolveClass: " + name +
                        "; Class: " + c + "; ClassLoader: " + c.getClassLoader());
            return c;
        }
        return super.loadClass(name);
    }

    private byte[] locateBytes() throws IOException {
        try {
            JarFile jar = new JarFile("Agent.jar");
            InputStream is = jar.getInputStream(jar.getEntry("Agent.class"));
            int len = is.available();
            byte[] buf = new byte[len];
            DataInputStream in = new DataInputStream(is);
            in.readFully(buf);
            return buf;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new IOException("Test failed due to IOException!");
        }
    }

    void appendToClassPathForInstrumentation(String path) {
        out.println("CustomLoader.appendToClassPathForInstrumentation: " +
                    this + ", jar: " + path);
        failed = false;
    }
}
