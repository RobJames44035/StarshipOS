/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @summary Testing ClassFile ExperimentalTransformExamples compilation.
 * @compile ExperimentalTransformExamples.java
 */
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;

import java.lang.classfile.*;
import java.lang.classfile.attribute.RuntimeInvisibleAnnotationsAttribute;
import java.lang.classfile.attribute.RuntimeVisibleAnnotationsAttribute;

/**
 * ExperimentalTransformExamples
 *
 */
public class ExperimentalTransformExamples {
    private static final FileSystem JRT = FileSystems.getFileSystem(URI.create("jrt:/"));

    static MethodTransform dropMethodAnnos = (mb, me) -> {
        if (!(me instanceof RuntimeVisibleAnnotationsAttribute || me instanceof RuntimeInvisibleAnnotationsAttribute))
            mb.with(me);
    };

    static FieldTransform dropFieldAnnos = (fb, fe) -> {
        if (!(fe instanceof RuntimeVisibleAnnotationsAttribute || fe instanceof RuntimeInvisibleAnnotationsAttribute))
            fb.with(fe);
    };

    public byte[] deleteAnnotations(ClassModel cm) {
        return ClassFile.of().transformClass(cm, (cb, ce) -> {
            switch (ce) {
                case MethodModel m -> cb.transformMethod(m, dropMethodAnnos);
                case FieldModel f -> cb.transformField(f, dropFieldAnnos);
                default -> cb.with(ce);
            }
        });
    }
}