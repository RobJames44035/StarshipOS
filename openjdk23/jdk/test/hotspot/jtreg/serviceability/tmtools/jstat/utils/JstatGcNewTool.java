/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package utils;

import common.TmTool;

/**
 * This tool executes "jstat -gcnew <pid>" and returns the results as
 * JstatGcNewResults
 */
public class JstatGcNewTool extends TmTool<JstatGcNewResults> {

    public JstatGcNewTool(long pid) {
        super(JstatGcNewResults.class, "jstat", "-gcnew " + pid);
    }

}
