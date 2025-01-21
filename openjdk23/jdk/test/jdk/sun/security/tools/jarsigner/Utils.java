/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipFile;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Helper class.
 */
public class Utils {

    static void createFiles(String... filenames) throws IOException {
        for (String filename : filenames) {
            new File(filename).createNewFile();
        }
    }

    static void printNonPrintableCharactersEscaped(byte[] manifest)
            throws IOException {
        // keep byte sequences encoding multi-byte UTF-8 encoded and composite
        // characters together as much as possible before decoding into a String
        ByteArrayOutputStream lineBuf = new ByteArrayOutputStream();
        for (int i = 0; i < manifest.length; i++) {
            switch (manifest[i]) {
            case '\t':
                lineBuf.write("\\t".getBytes(UTF_8));
                break;
            case '\r':
                lineBuf.write("\\r".getBytes(UTF_8));
                if (i + 1 >= manifest.length || manifest[i + 1] != '\n') {
                    System.out.println(lineBuf.toString(UTF_8));
                    lineBuf.reset();
                }
                break;
            case '\n':
                lineBuf.write("\\n".getBytes(UTF_8));
                System.out.println(lineBuf.toString(UTF_8));
                lineBuf.reset();
                break;
            default:
                lineBuf.write(manifest[i]);
            }
        }
        if (lineBuf.size() > 0) {
            System.out.println(lineBuf.toString(UTF_8));
        }
    }

    static void echoManifest(byte[] manifest, String msg) throws IOException {
        System.out.println("-".repeat(72));
        System.out.println(msg);
        System.out.println("-".repeat(72));
        printNonPrintableCharactersEscaped(manifest);
        System.out.println("-".repeat(72));
    }

    static byte[] readJarManifestBytes(String jarFilename) throws IOException {
        return readJarEntryBytes(jarFilename, JarFile.MANIFEST_NAME);
    }

    static byte[] readJarEntryBytes(String jarFilename, String jarEntryname)
            throws IOException {
        try (
            ZipFile jar = new ZipFile(jarFilename);
            InputStream is = jar.getInputStream(jar.getEntry(jarEntryname));
        ) {
            return is.readAllBytes();
        }
    }

    static String escapeStringWithNumbers(String lineBreak) {
        String escaped = "";
        byte[] bytes = lineBreak.getBytes(UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            escaped += bytes[i];
        }
        return escaped;
    }

}
