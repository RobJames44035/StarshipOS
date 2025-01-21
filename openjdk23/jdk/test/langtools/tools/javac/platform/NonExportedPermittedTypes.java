/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8318913
 * @summary Verify no error is when compiling a class whose permitted types are not exported
 * @modules jdk.compiler
 * @compile/fail/ref=NonExportedPermittedTypes.out -XDrawDiagnostics NonExportedPermittedTypes.java
 * @compile/fail/ref=NonExportedPermittedTypes.out --release 21 -XDrawDiagnostics NonExportedPermittedTypes.java
 * @compile/fail/ref=NonExportedPermittedTypes.out --release ${jdk.version} -XDrawDiagnostics NonExportedPermittedTypes.java
 */


import java.lang.constant.ConstantDesc;

public class NonExportedPermittedTypes {

    public void test1(ConstantDesc cd) {
        switch (cd) {
            case String s -> {}
        }
    }

    public void test2(ConstantDesc cd) {
        switch (cd) {
            case String s -> {}
            default -> {}
        }
    }

}
