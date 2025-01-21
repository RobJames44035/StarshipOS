/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package examples;


/**
 * An annotated binary node.
 *
 * <em>This example illustrates the use of simple annotations on
 * record components.</em>
 *
 * @param left  the left node
 * @param right the right node
 */
public record AnnotatedBinaryNode(@NonNull Node left, @NonNull Node right) { }

