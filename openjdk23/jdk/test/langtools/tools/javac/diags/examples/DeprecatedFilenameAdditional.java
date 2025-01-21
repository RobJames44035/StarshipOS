/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.note.deprecated.filename.additional
// key: compiler.warn.has.been.deprecated
// options: -Xlint:deprecation -Xmaxwarns 1

class DeprecatedFilename {
    DeprecatedClass d;
}

class DeprecatedFilenameAdditional {
    DeprecatedClass d;
}

@Deprecated
class DeprecatedClass { }
