/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */


import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdk.internal.jimage.BasicImageReader;
import jdk.internal.jimage.ImageLocation;

/**
 *
 * JDK Modular image iterator
 */
public class JImageHelper {

    private JImageHelper() {
        // Don't instantiate
    }

    public static List<String> listContents(Path jimage) throws IOException {
        try(BasicImageReader reader = BasicImageReader.open(jimage)) {
            List<String> entries = new ArrayList<>();
            for (String s : reader.getEntryNames()) {
                entries.add(s);
            }
            Collections.sort(entries);
            return entries;
        }
    }

    public static byte[] getLocationBytes(String location, Path jimage) throws IOException {
        try(BasicImageReader reader = BasicImageReader.open(jimage)) {
            ImageLocation il = reader.findLocation(location);
            byte[] r = reader.getResource(il);
            if (r == null) {
                throw new IllegalStateException(String.format("bytes for %s not found!", location));
            }
            return r;
        }
    }
}
