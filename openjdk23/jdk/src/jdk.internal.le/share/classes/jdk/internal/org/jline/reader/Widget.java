/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */
package jdk.internal.org.jline.reader;

/**
 *
 */
@FunctionalInterface
public interface Widget extends Binding {

    boolean apply();
}
