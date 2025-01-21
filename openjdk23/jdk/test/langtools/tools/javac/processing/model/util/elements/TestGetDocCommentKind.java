/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8307184
 * @summary Test basic operation of Elements.getDocCommentKind
 * @library /tools/lib /tools/javac/lib
 * @build   toolbox.ToolBox JavacTestingAbstractProcessor TestGetDocCommentKind
 * @compile -processor TestGetDocCommentKind -proc:only TestGetDocCommentKind.java
 */

import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.lang.model.util.*;
import javax.lang.model.util.Elements.DocCommentKind;

public class TestGetDocCommentKind extends JavacTestingAbstractProcessor {
    final Elements vacuousElements = new VacuousElements();

    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        if (!roundEnv.processingOver()) {
            boolean elementSeen = false;

            for (TypeElement typeRoot : ElementFilter.typesIn(roundEnv.getRootElements()) ) {
                for (Element element : typeRoot.getEnclosedElements()) {
                    ExpectedKind expectedKind = element.getAnnotation(ExpectedKind.class);
                    if (expectedKind != null ) {
                        elementSeen = true;

                        checkKind(element, elements, expectedKind.value());
                        checkKind(element, vacuousElements, null);
                    }
                }

                if (!elementSeen) {
                    throw new RuntimeException("No elements seen.");
                }
            }
        }
        return true;
    }

    void checkKind(Element e, Elements elementUtils, DocCommentKind expectedKind) {
        var actualKind = elementUtils.getDocCommentKind(e);
        if (actualKind != expectedKind) {
            messager.printError("Unexpected doc comment kind found: " + actualKind
                    + "expected: " + expectedKind, e);
        }
    }

    @interface ExpectedKind {
        DocCommentKind value();
    }

    /**
     * Traditional comment.
     */
    @ExpectedKind(DocCommentKind.TRADITIONAL)
    public void traditionalComment() { }

    /// End-of-line comment.
    @ExpectedKind(DocCommentKind.END_OF_LINE)
    public void endOfLineComment() { }
}
