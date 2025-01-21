/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

public class VictimClassLoader extends ClassLoader {
    public static long counter = 0;

    private int which = (int) ++counter;

    protected VictimClassLoader() {
        super(VictimClassLoader.class.getClassLoader());
    }

    protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class c;
        if (!name.endsWith("Victim")) {
            c = super.loadClass(name, resolve);
            return c;
        }

        c = findLoadedClass(name);
        if (c != null) {
            return c;
        }

        byte[] buf = readClassFile(name);
        c = defineClass(name, buf, 0, buf.length);
        resolveClass(c);

        if (c.getClassLoader() != this) {
            throw new AssertionError();
        }

        Test8003720.println("loaded " + c + "#" + System.identityHashCode(c) + " in " + c.getClassLoader());
        return c;
    }

    static byte[] readClassFile(String name) {
        try {
            String rname = name.substring(name.lastIndexOf('.') + 1) + ".class";
            java.net.URL url = VictimClassLoader.class.getResource(rname);
            Test8003720.println("found " + rname + " = " + url);

            java.net.URLConnection connection = url.openConnection();
            int contentLength = connection.getContentLength();
            byte[] buf = readFully(connection.getInputStream(), contentLength);

            return Asmator.fixup(buf);
        } catch (java.io.IOException ex) {
            throw new Error(ex);
        }
    }

    static byte[] readFully(java.io.InputStream in, int len) throws java.io.IOException {
        byte[] b = in.readAllBytes();
        if (len != -1 && b.length != len)
            throw new java.io.IOException("Expected:" + len + ", actual:" + b.length);
        return b;
    }

    public void finalize() {
        Test8003720.println("Goodbye from " + this);
    }

    public String toString() {
        return "VictimClassLoader#" + which;
    }
}
