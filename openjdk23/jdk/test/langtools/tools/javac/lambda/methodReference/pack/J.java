/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package pack;

import java.nio.file.Path;
import java.util.function.Function;

public class J {
    protected final Function<Path,String> fileReader;

    public J(Function<Path,String> fileReader) {
        this.fileReader = fileReader;
    }

    protected void checkFile(Path file) {
        fileReader.apply(file);
    }

    public void check(Path file) {
        checkFile(file);
    }
}
