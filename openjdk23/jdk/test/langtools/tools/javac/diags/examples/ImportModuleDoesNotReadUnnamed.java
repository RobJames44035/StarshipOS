/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

// key: compiler.err.import.module.does.not.read.unnamed
// key: compiler.note.preview.filename
// key: compiler.note.preview.recompile
// options: --release ${jdk.version} --enable-preview --limit-modules java.base

import module java.compiler;

public class ImportModuleDoesNotReadUnnamed {
}
