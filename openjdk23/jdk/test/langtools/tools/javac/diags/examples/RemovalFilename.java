/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

// key: compiler.note.removal.filename
// key: compiler.note.removal.recompile
// options: -Xlint:-removal

class RemovalFilename {
    RemovalClass d;
}

@Deprecated(forRemoval=true)
class RemovalClass { }
