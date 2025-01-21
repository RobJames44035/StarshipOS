/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package examples;

import java.io.Serializable;

/**
 * A serializable cartesian point.
 *
 * <em>This example illustrates the documentation of a serializable record.</em>
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record SerializablePoint(int x, int y) implements Serializable { }

