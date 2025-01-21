/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package javax.tools;

/**
 * Interface for receiving diagnostics from tools.
 *
 * @param <S> the type of source objects used by diagnostics received
 * by this listener
 *
 * @since 1.6
 */
public interface DiagnosticListener<S> {
    /**
     * Invoked when a problem is found.
     *
     * @param diagnostic a diagnostic representing the problem that
     * was found
     * @throws NullPointerException if the diagnostic argument is
     * {@code null} and the implementation cannot handle {@code null}
     * arguments
     */
    void report(Diagnostic<? extends S> diagnostic);
}
