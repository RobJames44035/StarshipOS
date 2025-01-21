/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package anttasks;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import propertiesparser.PropertiesParser;
import propertiesparser.gen.ClassGenerator;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Resource;

public class PropertiesParserTask extends MatchingTask {
    public void addSrc(Path src) {
        if (srcDirs == null)
            srcDirs = new Path(getProject());
        srcDirs.add(src);
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    @Override
    public void execute() {
        List<String> mainOpts = new ArrayList<String>();
        int count = 0;
        for (String dir : srcDirs.list()) {
            File baseDir = getProject().resolveFile(dir);
            DirectoryScanner s = getDirectoryScanner(baseDir);
            for (String path : s.getIncludedFiles()) {
                if (path.endsWith(".properties")) {
                    File srcFile = new File(baseDir, path);
                    String destPath =
                            path.substring(0, path.lastIndexOf(File.separator) + 1) +
                                    ClassGenerator.toplevelName(srcFile) + ".java";
                    File destFile = new File(this.destDir, destPath);
                    File destDir = destFile.getParentFile();
                    // Arguably, the comparison in the next line should be ">", not ">="
                    // but that assumes the resolution of the last modified time is fine
                    // grained enough; in practice, it is better to use ">=".
                    if (destFile.exists() && destFile.lastModified() >= srcFile.lastModified())
                        continue;
                    destDir.mkdirs();
                    mainOpts.add("-compile");
                    mainOpts.add(srcFile.getPath());
                    mainOpts.add(destDir.getPath());
                    count++;
                }
            }
        }
        if (mainOpts.size() > 0) {
            log("Generating " + count + " resource files to " + destDir, Project.MSG_INFO);
            PropertiesParser pp = new PropertiesParser(msg -> log(msg, Project.MSG_INFO));
            boolean ok = pp.run(mainOpts.toArray(new String[mainOpts.size()]));
            if (!ok)
                throw new BuildException("PropertiesParser failed.");
        }
    }

    private Path srcDirs;
    private File destDir;
}
