/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package utils;

import java.util.HashMap;

/**
 *
 * Represents stack of all threads + some extra information
 *
 */
public class JStack {

    private String date;
    private String vmVersion;
    private HashMap<String, ThreadStack> threads = new HashMap<String, ThreadStack>();
    private String jniGlobalReferences;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVmVersion() {
        return vmVersion;
    }

    public void setVmVersion(String vmVersion) {
        this.vmVersion = vmVersion;
    }

    public HashMap<String, ThreadStack> getThreads() {
        return threads;
    }

    public void setThreads(HashMap<String, ThreadStack> threads) {
        this.threads = threads;
    }

    public void addThreadStack(String threadName, ThreadStack ts) {
        System.out.println("Adding thread stack for thread: " + threadName);
        threads.put(threadName, ts);
    }

    public String getJniGlobalReferences() {
        return jniGlobalReferences;
    }

    public void setJniGlobalReferences(String jniGlobalReferences) {
        this.jniGlobalReferences = jniGlobalReferences;
    }

    public ThreadStack getThreadStack(String threadName) {
        return threads.get(threadName);
    }

}
