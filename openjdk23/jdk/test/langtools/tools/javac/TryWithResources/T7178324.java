/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7178324
 * @summary Crash when compiling for(i : x) try(AutoCloseable x = ...) {}
 * @compile T7178324.java
 */

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

class T7178324 {
    public static void main(String[] args) throws Exception {
        List<File> files = new ArrayList<>();
        for (File f : files)
            try (FileInputStream is = new FileInputStream(f)) {
        }
    }
}
