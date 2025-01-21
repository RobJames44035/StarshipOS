/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 *
 */

package bench.rmi;

import bench.Benchmark;
import java.rmi.server.RMIClassLoader;
import java.security.CodeSource;

/**
 * Benchmark for testing speed of repeated loading of a class not found in
 * classpath.
 */
public class ClassLoading implements Benchmark {

    static final String ALTROOT = "!/bench/rmi/altroot/";
    static final String CLASSNAME = "Node";

    /**
     * Repeatedly load a class not found in classpath through RMIClassLoader.
     * Arguments: <# reps>
     */
    public long run(String[] args) throws Exception {
        int reps = Integer.parseInt(args[0]);
        CodeSource csrc = getClass().getProtectionDomain().getCodeSource();
        String url = "jar:" + csrc.getLocation().toString() + ALTROOT;

        long start = System.currentTimeMillis();
        for (int i = 0; i < reps; i++)
            RMIClassLoader.loadClass(url, CLASSNAME);
        long time = System.currentTimeMillis() - start;

        return time;
    }
}
