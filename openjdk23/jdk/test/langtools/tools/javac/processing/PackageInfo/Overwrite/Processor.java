/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOError;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

@SupportedAnnotationTypes("*")
public class Processor extends JavacTestingAbstractProcessor {

    boolean first = true;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (first) {
            // Annotations are present on the initial package-info loaded from the classpath.
            PackageElement p = processingEnv.getElementUtils().getPackageElement("p");
            if (p.getAnnotationMirrors().isEmpty()) {
                throw new AssertionError(
                        "expected package annotations: " + p.getAnnotationMirrors());
            }
            // Overwrite the package-info with a new unannotated package-info.
            try (OutputStream os =
                    processingEnv
                            .getFiler()
                            .createSourceFile("p.package-info")
                            .openOutputStream()) {
                os.write("package p;".getBytes(UTF_8));
            } catch (IOException e) {
                throw new IOError(e);
            }
            first = false;
        }
        // The package-info's symbol should be reset between rounds, and when annotation
        // processing is over the package-info should be unannotated.
        PackageElement p = processingEnv.getElementUtils().getPackageElement("p");
        if (roundEnv.processingOver()) {
            if (!p.getAnnotationMirrors().isEmpty()) {
                throw new AssertionError(
                        "expected no package annotations: " + p.getAnnotationMirrors());
            }
        }
        return false;
    }
}
