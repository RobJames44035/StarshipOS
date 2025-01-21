/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

// key: compiler.err.default.and.both.boolean.values
// key: compiler.note.preview.filename
// key: compiler.note.preview.recompile
// options: --enable-preview --source ${jdk.version}
public class DefaultAndBothBoolean {
    private int test(boolean sel) {
        return switch (sel) {
            case true -> 1;
            case false -> 2;
            default -> 3;
        };
    }
}