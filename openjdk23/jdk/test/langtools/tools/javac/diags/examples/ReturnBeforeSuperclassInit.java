/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

// key: compiler.err.return.before.superclass.initialized
// key: compiler.note.preview.filename
// key: compiler.note.preview.recompile
// options: --enable-preview -source ${jdk.version}

class ReturnBeforeSuperclassInit {
    ReturnBeforeSuperclassInit(boolean maybe) {
        if (maybe)
            return;
        super();
    }
}
