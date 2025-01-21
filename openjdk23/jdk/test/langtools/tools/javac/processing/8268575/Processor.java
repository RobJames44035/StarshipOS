/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;
import java.util.List;

@SupportedAnnotationTypes("*")
public class Processor extends AbstractProcessor {

  @Override
  public SourceVersion getSupportedSourceVersion() {
    return SourceVersion.latestSupported();
  }

  int round = 1;

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    processingEnv.getMessager().printNote("round " + round);
    Element t = processingEnv.getElementUtils().getTypeElement("T8268575");
    for (Element e : t.getEnclosedElements()) {
      if (e instanceof ExecutableElement) {
        for (VariableElement p : ((ExecutableElement) e).getParameters()) {
            List<? extends AnnotationMirror> annos = p.getAnnotationMirrors();
            if (annos.size() != 1) {
                throw new RuntimeException("Missing annotation in round " + round);
            }
        }
      }
    }
    if (round == 1) {
      String name = "A";
      try {
        JavaFileObject jfo = processingEnv.getFiler().createSourceFile(name);
        try (Writer w = jfo.openWriter()) {
          w.write("@interface " + name + " {}");
        }
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
    round++;
    return false;
  }
}
