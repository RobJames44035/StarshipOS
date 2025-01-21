/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.err.report.access

class ReportAccess {
    int i = new Other().x;
}

class Other {
    private int x;
}
