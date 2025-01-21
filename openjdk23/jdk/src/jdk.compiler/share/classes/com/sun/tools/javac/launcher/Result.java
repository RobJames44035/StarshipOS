/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

package com.sun.tools.javac.launcher;

import java.util.Set;

/**
 * Contains information about the launched program.
 *
 * <p><strong>This is NOT part of any supported API.
 * If you write code that depends on this, you do so at your own
 * risk.  This code and its internal interfaces are subject to change
 * or deletion without notice.</strong></p>
 *
 * @param programClass the class instance of the launched program.
 * @param classNames the names of classes compiled into memory.
 */
public record Result(Class<?> programClass, Set<String> classNames) {}
