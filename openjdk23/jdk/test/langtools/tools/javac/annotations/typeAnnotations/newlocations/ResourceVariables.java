/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

import java.lang.annotation.*;

import java.io.*;

/*
 * @test
 * @bug 6843077 8006775
 * @summary new type annotation location: resource variables
 * @author Werner Dietl
 * @compile ResourceVariables.java
 */

class ResourceVariables {
    void m() throws Exception {
        try (@A InputStream is = new @B FileInputStream("xxx")) {
        }
    }
}

@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface A { }
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@interface B { }
