/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package jdk.internal.classfile.impl;

import java.lang.classfile.CodeModel;
import java.lang.classfile.MethodBuilder;

public sealed interface TerminalMethodBuilder
        extends MethodBuilder, MethodInfo
        permits BufferedMethodBuilder, DirectMethodBuilder {
    BufferedCodeBuilder bufferedCodeBuilder(CodeModel original);
}
