/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package sun.net.www.protocol.jar;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.jar.*;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.security.CodeSigner;
import java.security.cert.Certificate;
import sun.net.www.ParseUtil;

/* URL jar file is a common JarFile subtype used for JarURLConnection */
public class URLJarFile extends JarFile {

    /* Controller of the Jar File's closing */
    private URLJarFileCloseController closeController = null;

    private Manifest superMan;
    private Attributes superAttr;
    private Map<String, Attributes> superEntries;

    static JarFile getJarFile(URL url, URLJarFileCloseController closeController) throws IOException {
        if (isFileURL(url)) {
            Runtime.Version version = "runtime".equals(url.getRef())
                    ? JarFile.runtimeVersion()
                    : JarFile.baseVersion();
            return new URLJarFile(url, closeController, version);
        } else {
            return retrieve(url, closeController);
        }
    }

    private URLJarFile(File file, URLJarFileCloseController closeController, Runtime.Version version)
            throws IOException {
        super(file, true, ZipFile.OPEN_READ | ZipFile.OPEN_DELETE, version);
        this.closeController = closeController;
    }

    private URLJarFile(URL url, URLJarFileCloseController closeController, Runtime.Version version)
            throws IOException {
        super(new File(ParseUtil.decode(url.getFile())), true, ZipFile.OPEN_READ, version);
        this.closeController = closeController;
    }

    static boolean isFileURL(URL url) {
        if (url.getProtocol().equalsIgnoreCase("file")) {
            /*
             * Consider this a 'file' only if it's a LOCAL file, because
             * 'file:' URLs can be accessible through ftp.
             */
            String host = url.getHost();
            if (host == null || host.isEmpty() || host.equals("~") ||
                host.equalsIgnoreCase("localhost"))
                return true;
        }
        return false;
    }

    /**
     * Returns the <code>ZipEntry</code> for the given entry name or
     * <code>null</code> if not found.
     *
     * @param name the JAR file entry name
     * @return the <code>ZipEntry</code> for the given entry name or
     *         <code>null</code> if not found
     * @see java.util.zip.ZipEntry
     */
    public ZipEntry getEntry(String name) {
        ZipEntry ze = super.getEntry(name);
        if (ze != null) {
            if (ze instanceof JarEntry)
                return new URLJarFileEntry((JarEntry)ze);
            else
                throw new InternalError(super.getClass() +
                                        " returned unexpected entry type " +
                                        ze.getClass());
        }
        return null;
    }

    public Manifest getManifest() throws IOException {

        if (!isSuperMan()) {
            return null;
        }

        Manifest man = new Manifest();
        Attributes attr = man.getMainAttributes();
        attr.putAll((Map)superAttr.clone());

        // now deep copy the manifest entries
        if (superEntries != null) {
            Map<String, Attributes> entries = man.getEntries();
            for (String key : superEntries.keySet()) {
                Attributes at = superEntries.get(key);
                entries.put(key, (Attributes) at.clone());
            }
        }

        return man;
    }

    /* If close controller is set the notify the controller about the pending close */
    public void close() throws IOException {
        if (closeController != null) {
            closeController.close(this);
        }
        super.close();
    }

    // optimal side-effects
    private synchronized boolean isSuperMan() throws IOException {

        if (superMan == null) {
            superMan = super.getManifest();
        }

        if (superMan != null) {
            superAttr = superMan.getMainAttributes();
            superEntries = superMan.getEntries();
            return true;
        } else
            return false;
    }

    /**
     * Given a URL, retrieves a JAR file, caches it to disk, and creates a
     * cached JAR file object.
     */
    private static JarFile retrieve(final URL url, final URLJarFileCloseController closeController) throws IOException {
        Runtime.Version version = "runtime".equals(url.getRef())
                ? JarFile.runtimeVersion()
                : JarFile.baseVersion();
        try (final InputStream in = url.openConnection().getInputStream()) {
            Path tmpFile = Files.createTempFile("jar_cache", null);
            try {
                Files.copy(in, tmpFile, StandardCopyOption.REPLACE_EXISTING);
                JarFile jarFile = new URLJarFile(tmpFile.toFile(), closeController, version);
                tmpFile.toFile().deleteOnExit();
                return jarFile;
            } catch (Throwable thr) {
                try {
                    Files.delete(tmpFile);
                } catch (IOException ioe) {
                    thr.addSuppressed(ioe);
                }
                throw thr;
            }
        }
    }

    private class URLJarFileEntry extends JarEntry {
        private final JarEntry je;

        URLJarFileEntry(JarEntry je) {
            super(je);
            this.je = je;
        }

        public Attributes getAttributes() throws IOException {
            if (URLJarFile.this.isSuperMan()) {
                Map<String, Attributes> e = URLJarFile.this.superEntries;
                if (e != null) {
                    Attributes a = e.get(getName());
                    if (a != null)
                        return  (Attributes)a.clone();
                }
            }
            return null;
        }

        public java.security.cert.Certificate[] getCertificates() {
            Certificate[] certs = je.getCertificates();
            return certs == null? null: certs.clone();
        }

        public CodeSigner[] getCodeSigners() {
            CodeSigner[] csg = je.getCodeSigners();
            return csg == null? null: csg.clone();
        }
    }

    public interface URLJarFileCloseController {
        public void close(JarFile jarFile);
    }
}
