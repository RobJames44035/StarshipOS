/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8300857
 * @summary Test Types.asElement in cases specified to return null
 * @library /tools/javac/lib
 * @build   JavacTestingAbstractProcessor TestAsElement
 * @compile -processor TestAsElement -proc:only TestAsElement.java
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import static java.util.Objects.*;

/**
 * Verify various kinds of TypeMirror have a null corresponding element.
 */
public class TestAsElement extends JavacTestingAbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            testNullCases();
            testRoundTripCases();

        }
        return true;
    }

    private void testNullCases() {
        // Test all primitive types
        for (TypeKind typeKind : TypeKind.values()) {
            if (typeKind.isPrimitive() ) {
                expectNullAsElement(typeUtils.getPrimitiveType(typeKind));
            }
        }
        expectNullAsElement(typeUtils.getNoType(TypeKind.VOID));
        expectNullAsElement(typeUtils.getNoType(TypeKind.NONE));
        expectNullAsElement(typeUtils.getNullType());

        Element objectElement = eltUtils.getTypeElement("java.lang.Object");
        expectNullAsElement(typeUtils.getWildcardType(objectElement.asType(), null));

        // Loop over the ExecutableTypes for Object's methods
        for(var methodElt : ElementFilter.methodsIn(objectElement.getEnclosedElements())) {
            expectNullAsElement(methodElt.asType());
        }
    }

    private void expectNullAsElement(TypeMirror typeMirror) {
        var e = typeUtils.asElement(typeMirror);
        if (e != null) {
            throw new RuntimeException("Unexpected non-null value " + e);
        }
    }

    private void testRoundTripCases() {
        expectRoundTrip(eltUtils.getTypeElement("java.lang.String"));
        expectRoundTrip(eltUtils.getPackageElement("java.lang"));
        expectRoundTrip(eltUtils.getModuleElement("java.base"));
    }

    private void expectRoundTrip(Element e) {
        var type = e.asType();

        if (! typeUtils.asElement(type).equals(e)) {
            throw new RuntimeException("Did not see round trip elt -> type -> elt on " + e);
        }
    }
}
