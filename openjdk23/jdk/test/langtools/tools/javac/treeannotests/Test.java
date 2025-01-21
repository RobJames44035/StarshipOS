/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * Annotation used by TestProcessor to indicate the expected number of
 * @DA and @TA annotations in the source tree to which the Test annotation
 * is attached.
 */
@interface Test {
    int value();
}

