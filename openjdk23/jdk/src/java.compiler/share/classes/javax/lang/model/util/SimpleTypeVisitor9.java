/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package javax.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import static javax.lang.model.SourceVersion.*;

/**
 * A simple visitor of types with default behavior appropriate for
 * source versions {@link SourceVersion#RELEASE_9 RELEASE_9} through
 * {@link SourceVersion#RELEASE_14 RELEASE_14}.
 *
 * Visit methods corresponding to {@code RELEASE_9} and earlier
 * language constructs call {@link #defaultAction defaultAction},
 * passing their arguments to {@code defaultAction}'s corresponding
 * parameters.
 *
 * @apiNote
 * Methods in this class may be overridden subject to their general
 * contract.
 *
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @see SimpleTypeVisitor6##note_for_subclasses
 * <strong>Compatibility note for subclasses</strong>
 * @see SimpleTypeVisitor6
 * @see SimpleTypeVisitor7
 * @see SimpleTypeVisitor8
 * @see SimpleTypeVisitor14
 * @since 9
 */
@SupportedSourceVersion(RELEASE_14)
public class SimpleTypeVisitor9<R, P> extends SimpleTypeVisitor8<R, P> {
    /**
     * Constructor for concrete subclasses; uses {@code null} for the
     * default value.
     */
    protected SimpleTypeVisitor9(){
        super(null);
    }

    /**
     * Constructor for concrete subclasses; uses the argument for the
     * default value.
     *
     * @param defaultValue the value to assign to {@link #DEFAULT_VALUE}
     */
    protected SimpleTypeVisitor9(R defaultValue){
        super(defaultValue);
    }
}
