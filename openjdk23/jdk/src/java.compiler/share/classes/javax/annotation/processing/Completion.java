/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

package javax.annotation.processing;

/**
 * A suggested {@linkplain Processor#getCompletions <em>completion</em>} for an
 * annotation.  A completion is text meant to be inserted into a
 * program as part of an annotation.
 *
 * @since 1.6
 */
public interface Completion {

    /**
     * {@return the text of the suggested completion}
     */
    String getValue();

    /**
     * {@return an informative message about the completion}
     */
    String getMessage();
}
