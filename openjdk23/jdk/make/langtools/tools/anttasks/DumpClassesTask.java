/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package anttasks;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.stream.Stream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class DumpClassesTask extends Task {

    private String moduleName;
    private File dir;

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setDestDir(File dir) {
        this.dir = dir;
    }

    @Override
    public void execute() {
        try (FileSystem fs = FileSystems.newFileSystem(new URI("jrt:/"), Collections.emptyMap(), DumpClassesTask.class.getClassLoader())) {
            Path source = fs.getPath("modules", moduleName);
            Path target = dir.toPath();

            try (Stream<Path> content = Files.walk(source)) {
                content.filter(Files :: isRegularFile)
                       .forEach(p -> {
                    try {
                        Path targetFile = target.resolve(source.relativize(p).toString());
                        if (!Files.exists(targetFile) || Files.getLastModifiedTime(targetFile).compareTo(Files.getLastModifiedTime(source)) < 0) {
                            Files.createDirectories(targetFile.getParent());
                            Files.copy(p, targetFile, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException ex) {
                        throw new UncheckedIOException(ex);
                    }
                });
            }
        } catch (URISyntaxException | IOException | UncheckedIOException ex) {
            throw new BuildException(ex);
        }
    }
}
