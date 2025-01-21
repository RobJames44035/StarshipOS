/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */
package utils;

import common.TmTool;

/**
 * This tool executes "jstat -gc <pid>" and returns the results as
 * JstatGcToolResults
 */
public class JstatGcCauseTool extends TmTool<JstatGcCauseResults> {

    public JstatGcCauseTool(long pid) {
        super(JstatGcCauseResults.class, "jstat", "-gc " + pid);
    }

}
