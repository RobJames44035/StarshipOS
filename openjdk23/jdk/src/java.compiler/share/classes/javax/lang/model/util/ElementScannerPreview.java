/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package javax.lang.model.util;

import jdk.internal.javac.PreviewFeature;

import javax.annotation.processing.SupportedSourceVersion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import static javax.lang.model.SourceVersion.*;

/**
 * A scanning visitor of program elements with default behavior
 * appropriate for a {@linkplain
 * ProcessingEnvironment#isPreviewEnabled preview} source version.
 *
 * The <code>visit<i>Xyz</i></code> methods in this class scan their
 * component elements by calling {@link ElementScanner6#scan(Element,
 * Object) scan} on their {@linkplain Element#getEnclosedElements
 * enclosed elements}, {@linkplain ExecutableElement#getParameters
 * parameters}, etc., as indicated in the individual method
 * specifications.  A subclass can control the order elements are
 * visited by overriding the <code>visit<i>Xyz</i></code> methods.
 * Note that clients of a scanner may get the desired behavior by
 * invoking {@code v.scan(e, p)} rather than {@code v.visit(e, p)} on
 * the root objects of interest.
 *
 * <p>When a subclass overrides a <code>visit<i>Xyz</i></code> method, the
 * new method can cause the enclosed elements to be scanned in the
 * default way by calling <code>super.visit<i>Xyz</i></code>.  In this
 * fashion, the concrete visitor can control the ordering of traversal
 * over the component elements with respect to the additional
 * processing; for example, consistently calling
 * <code>super.visit<i>Xyz</i></code> at the start of the overridden
 * methods will yield a preorder traversal, etc.  If the component
 * elements should be traversed in some other order, instead of
 * calling <code>super.visit<i>Xyz</i></code>, an overriding visit method
 * should call {@code scan} with the elements in the desired order.
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
 * @see javax.lang.model.util##expectedEvolution
 * <strong>Expected visitor evolution</strong>
 * @see AbstractAnnotationValueVisitor6##note_for_subclasses
 * <strong>Compatibility note for subclasses</strong>
 * @see ElementScanner6
 * @see ElementScanner7
 * @see ElementScanner8
 * @see ElementScanner9
 * @see ElementScanner14
 * @since 23
 */
@SupportedSourceVersion(RELEASE_25)
@PreviewFeature(feature=PreviewFeature.Feature.LANGUAGE_MODEL, reflective=true)
public class ElementScannerPreview<R, P> extends ElementScanner14<R, P> {
    /**
     * Constructor for concrete subclasses; uses {@code null} for the
     * default value.
     */
    protected ElementScannerPreview(){
        super(null);
    }

    /**
     * Constructor for concrete subclasses; uses the argument for the
     * default value.
     *
     * @param defaultValue the default value
     */
    protected ElementScannerPreview(R defaultValue){
        super(defaultValue);
    }
}
