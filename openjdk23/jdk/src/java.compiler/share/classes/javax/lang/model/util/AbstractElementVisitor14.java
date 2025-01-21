/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package javax.lang.model.util;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.RecordComponentElement;
import static javax.lang.model.SourceVersion.*;

/**
 * A skeletal visitor of program elements with default behavior
 * appropriate for the {@link SourceVersion#RELEASE_14 RELEASE_14}
 * source version.
 *
 * @param <R> the return type of this visitor's methods.  Use {@link
 *            Void} for visitors that do not need to return results.
 * @param <P> the type of the additional parameter to this visitor's
 *            methods.  Use {@code Void} for visitors that do not need an
 *            additional parameter.
 *
 * @see AbstractElementVisitor6##note_for_subclasses
 * <strong>Compatibility note for subclasses</strong>
 * @see AbstractElementVisitor6
 * @see AbstractElementVisitor7
 * @see AbstractElementVisitor8
 * @see AbstractElementVisitor9
 * @since 16
 */
@SupportedSourceVersion(RELEASE_25)
public abstract class AbstractElementVisitor14<R, P> extends AbstractElementVisitor9<R, P> {
    /**
     * Constructor for concrete subclasses to call.
     */
    protected AbstractElementVisitor14(){
        super();
    }

    /**
     * {@inheritDoc ElementVisitor}
     *
     * @implSpec Visits a {@code RecordComponentElement} in a manner defined by a
     * subclass.
     *
     * @param e  {@inheritDoc ElementVisitor}
     * @param p  {@inheritDoc ElementVisitor}
     * @return   {@inheritDoc ElementVisitor}
     */
    @Override
    public abstract R visitRecordComponent(RecordComponentElement e, P p);
}
