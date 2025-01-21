/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 6956385
 * @summary JarURLConnection may fail to close its underlying FileURLConnection
 * @run main/othervm FileURLConnectionLeak
 */

import java.net.URI;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.jar.Attributes;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
public class FileURLConnectionLeak {
    public static void main(String[] args) throws Exception {
        URLConnection.setDefaultUseCaches("file", false);
        URLConnection.setDefaultUseCaches("jar", false);
        var jar = Path.of("x.jar").toAbsolutePath();
        var mani = new Manifest();
        mani.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        try (var os = Files.newOutputStream(jar); var jos = new JarOutputStream(os, mani)) {}
        var u = URI.create("jar:" + jar.toUri() + "!/META-INF/MANIFEST.MF").toURL();
        // FileURLConnection.is not used, so was always fine:
        try (var is = u.openStream()) {
            is.transferTo(System.out);
        }
        // FileURLConnection.is opened implicitly:
        var conn = u.openConnection();
        conn.getLastModified();
        // Idiom to close URLConnection (cf. JDK-8224095), which must also close the other stream:
        conn.getInputStream().close();
        var fds = Path.of("/proc/" + ProcessHandle.current().pid() + "/fd");
        if (Files.isDirectory(fds)) {
            // Linux: verify that x.jar is not open
            for (var fd : (Iterable<Path>) Files.list(fds)::iterator) {
                if (Files.isSymbolicLink(fd)) {
                    var file = Files.readSymbolicLink(fd);
                    if (file.equals(jar)) {
                        throw new IllegalStateException("Still held open " + jar + " from " + fd);
                    }
                }
            }
        }
        // Windows: verify that mandatory file locks do not prevent deletion
        Files.delete(jar);
    }
}
