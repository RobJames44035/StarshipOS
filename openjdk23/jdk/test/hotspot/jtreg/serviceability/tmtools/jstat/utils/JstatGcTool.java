/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package utils;

import common.TmTool;

/**
 * This tool executes "jstat -gc <pid>" and returns the results as
 * JstatGcToolResults
 */
public class JstatGcTool extends TmTool<JstatGcResults> {

    public JstatGcTool(long pid) {
        super(JstatGcResults.class, "jstat", "-gc " + pid);
    }

}
