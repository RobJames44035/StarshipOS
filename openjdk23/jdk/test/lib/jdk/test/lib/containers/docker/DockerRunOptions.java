/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

package jdk.test.lib.containers.docker;

import java.util.ArrayList;
import java.util.Collections;


// This class represents options for running java inside docker containers
// in test environment.
public class DockerRunOptions {
    public String imageNameAndTag;
    public ArrayList<String> dockerOpts = new ArrayList<>();
    public String command;    // normally a full path to java
    public ArrayList<String> javaOpts = new ArrayList<>();
    // more java options, but to be set AFTER the test Java options
    public ArrayList<String> javaOptsAppended = new ArrayList<>();
    public String classToRun;  // class or "-version"
    public ArrayList<String> classParams = new ArrayList<>();

    public boolean tty = true;
    public boolean removeContainerAfterUse = true;
    public boolean appendTestJavaOptions = true;
    public boolean retainChildStdout = false;

    /**
     * Convenience constructor for most common use cases in testing.
     * @param imageNameAndTag  a string representing name and tag for the
     *        docker image to run, as "name:tag"
     * @param javaCmd  a java command to run (e.g. /jdk/bin/java)
     * @param classToRun  a class to run, or "-version"
     * @param javaOpts  java options to use
     *
     * @return Default docker run options
     */
    public DockerRunOptions(String imageNameAndTag, String javaCmd,
                            String classToRun, String... javaOpts) {
        this.imageNameAndTag = imageNameAndTag;
        this.command = javaCmd;
        this.classToRun = classToRun;
        this.addJavaOpts(javaOpts);
        // always print hserr to stderr in the docker tests to avoid
        // trouble accessing it after a crash in the container
        this.addJavaOpts("-XX:+ErrorFileToStderr");
    }

    public final DockerRunOptions addDockerOpts(String... opts) {
        Collections.addAll(dockerOpts, opts);
        return this;
    }

    public final DockerRunOptions addJavaOpts(String... opts) {
        Collections.addAll(javaOpts, opts);
        return this;
    }

    public final DockerRunOptions addJavaOptsAppended(String... opts) {
        Collections.addAll(javaOptsAppended, opts);
        return this;
    }

    public final DockerRunOptions addClassOptions(String... opts) {
        Collections.addAll(classParams,opts);
        return this;
    }
}
