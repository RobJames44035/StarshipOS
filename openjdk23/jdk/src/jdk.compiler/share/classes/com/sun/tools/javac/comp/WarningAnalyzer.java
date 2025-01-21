/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package com.sun.tools.javac.comp;

import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;

/** This pass checks for various things to warn about.
 *  It runs after attribution and flow analysis.
 *
 *  <p><b>This is NOT part of any supported API.
 *  If you write code that depends on this, you do so at your own risk.
 *  This code and its internal interfaces are subject to change or
 *  deletion without notice.</b>
 */
public class WarningAnalyzer {

    protected static final Context.Key<WarningAnalyzer> contextKey = new Context.Key<>();

    private final Log log;
    private final ThisEscapeAnalyzer thisEscapeAnalyzer;

    public static WarningAnalyzer instance(Context context) {
        WarningAnalyzer instance = context.get(contextKey);
        if (instance == null)
            instance = new WarningAnalyzer(context);
        return instance;
    }

    @SuppressWarnings("this-escape")
    protected WarningAnalyzer(Context context) {
        context.put(contextKey, this);
        log = Log.instance(context);
        thisEscapeAnalyzer = ThisEscapeAnalyzer.instance(context);
    }

    public void analyzeTree(Env<AttrContext> env) {
        thisEscapeAnalyzer.analyzeTree(env);
    }
}
