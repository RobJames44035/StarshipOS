/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

/*
 * @test
 * @bug 6794071
 * @summary Test that exceptions have a proper parent class
 * @author  Joseph D. Darcy
 * @modules java.compiler
 *          jdk.compiler
 */

import javax.lang.model.UnknownEntityException;
import javax.lang.model.element.*;
import javax.lang.model.type.*;

/*
 * Verify UnknownFooExceptions can be caught with a common parent
 * exception.
 */
public class TestExceptions {
    public static void main(String... args) {
        RuntimeException[] exceptions = {
            new UnknownElementException((Element)null, (Object)null),
            new UnknownAnnotationValueException((AnnotationValue) null, (Object) null),
            new UnknownTypeException((TypeMirror)null, (Object)null)
        };

        for(RuntimeException exception : exceptions) {
            try {
                throw exception;
            } catch (UnknownEntityException uee) {
                ;
            }
        }
    }
}
