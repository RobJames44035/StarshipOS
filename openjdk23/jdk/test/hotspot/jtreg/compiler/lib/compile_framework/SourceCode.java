/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package compiler.lib.compile_framework;

/**
 * This class represents the source code of a specific class.
 */
record SourceCode(String className, String extension, String code) {
    public String filePathName() {
        StringBuilder builder = new StringBuilder();
        builder.append(className.replace('.','/')).append(".").append(extension);
        return builder.toString();
    }
}
