/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */
package org.netbeans.jemmy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarFile;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 *
 * Load string resources from file. Resources should be stored in
 * {@code name=value} format.
 *
 * @see org.netbeans.jemmy.BundleManager
 *
 * @author Alexandre Iline (alexandre.iline@oracle.com)
 */
public class Bundle extends Object {

    private Properties resources;

    /**
     * Bunble constructor.
     */
    public Bundle() {
        resources = new Properties();
    }

    /**
     * Loads resources from an input stream.
     *
     * @param stream Stream to load resources from.
     * @exception IOException
     */
    public void load(InputStream stream)
            throws IOException {
        resources.load(stream);
    }

    /**
     * Loads resources from a simple file.
     *
     * @param fileName Name of the file to load resources from.
     * @exception IOException
     * @exception FileNotFoundException
     */
    public void loadFromFile(String fileName)
            throws IOException, FileNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            load(fileInputStream);
        }
    }

    /**
     * Loads resources from a file in a jar archive.
     *
     * @param fileName Name of the jar archive.
     * @param entryName ?enryName? Name of the file to load resources from.
     * @exception IOException
     * @exception FileNotFoundException
     */
    public void loadFromJar(String fileName, String entryName)
            throws IOException, FileNotFoundException {
        try (JarFile jFile = new JarFile(fileName);
                InputStream inputStream = jFile.getInputStream(jFile.getEntry(entryName))) {
            load(inputStream);
        }
    }

    /**
     * Loads resources from a file in a zip archive.
     *
     * @param fileName Name of the zip archive.
     * @param entryName ?enryName? Name of the file to load resources from.
     * @exception ZipException
     * @exception IOException
     * @exception FileNotFoundException
     */
    public void loadFromZip(String fileName, String entryName)
            throws IOException, FileNotFoundException, ZipException {
        try (ZipFile zFile = new ZipFile(fileName);
                InputStream inputStream = zFile.getInputStream(zFile.getEntry(entryName))) {
            load(inputStream);
        }
    }

    /**
     * Prints bundle contents.
     *
     * @param writer Writer to print data in.
     */
    public void print(PrintWriter writer) {
        Enumeration<Object> keys = resources.keys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            writer.println(key + "=" + getResource(key));
        }
    }

    /**
     * Prints bundle contents.
     *
     * @param stream Stream to print data in.
     */
    public void print(PrintStream stream) {
        print(new PrintWriter(stream));
    }

    /**
     * Gets resource by key.
     *
     * @param key Resource key
     * @return Resource value or null if resource was not found.
     */
    public String getResource(String key) {
        return resources.getProperty(key);
    }

}
