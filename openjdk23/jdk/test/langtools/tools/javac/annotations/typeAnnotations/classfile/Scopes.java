/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.lang.annotation.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.lang.classfile.*;
import java.lang.classfile.attribute.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary Qualified inner type annotation accessible to the class.
 */

@Scopes.UniqueInner
public class Scopes<T extends @Scopes.UniqueInner Object> extends ClassfileTestHelper{
    public static void main(String[] args) throws Exception {
        new Scopes().run();
    }

    public void run() throws Exception {
        expected_tinvisibles = 1;
        expected_invisibles = 1;

        ClassModel cm = getClassFile("Scopes.class");
        test(cm);

        countAnnotations();

        if (errors > 0)
            throw new Exception(errors + " errors found");
        System.out.println("PASSED");
    }

    @Target({ElementType.TYPE_USE})
    @interface UniqueInner { };
}
