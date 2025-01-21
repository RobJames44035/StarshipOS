/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.io.*;
import java.lang.constant.ClassDesc;
import java.lang.constant.ConstantDescs;
import java.lang.constant.MethodTypeDesc;
import java.util.*;
import javax.annotation.processing.*;
import javax.lang.model.*;
import javax.lang.model.element.*;
import javax.tools.*;
import java.lang.reflect.AccessFlag;

import java.lang.classfile.ClassFile;

/* Create an invalid classfile with version 51.0 and a non-abstract method in an interface.*/
@SupportedAnnotationTypes("*")
public class CreateBadClassFile extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> elems, RoundEnvironment renv) {
        if (++round == 1) {
            byte[] bytes = ClassFile.of().build(ClassDesc.of("Test"), classBuilder -> {
                classBuilder.withVersion(51, 0);
                classBuilder.withFlags(AccessFlag.ABSTRACT ,
                                          AccessFlag.INTERFACE ,
                                          AccessFlag.PUBLIC);
                classBuilder.withMethod("test", MethodTypeDesc.of(ConstantDescs.CD_void), ClassFile.ACC_PUBLIC, methodBuilder -> {
                    methodBuilder.withFlags(AccessFlag.PUBLIC);});
                });
            try {
                JavaFileObject clazz = processingEnv.getFiler().createClassFile("Test");
                try (OutputStream out = clazz.openOutputStream()) {
                    out.write(bytes);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    int round = 0;
}
