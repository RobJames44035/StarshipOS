/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8344824
 * @summary CDS dump crashes when member_method of a lambda proxy is null
 * @requires vm.cds
 * @library /test/lib
 * @build LambdaInvokeVirtual
 * @run driver jdk.test.lib.helpers.ClassFileInstaller -jar LambdaInvokeVirtualApp.jar LambdaInvokeVirtualApp MyFunctionalInterface
 * @run driver LambdaInvokeVirtual
 */

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import jdk.test.lib.cds.CDSOptions;
import jdk.test.lib.cds.CDSTestUtils;
import jdk.test.lib.helpers.ClassFileInstaller;
import jdk.test.lib.process.OutputAnalyzer;

public class LambdaInvokeVirtual {

    public static void main(String[] args) throws Exception {
        String appJar =  ClassFileInstaller.getJarPath("LambdaInvokeVirtualApp.jar");
        String mainClass = LambdaInvokeVirtualApp.class.getName();
        String namePrefix = "LambdaInvokeVirtualApp";
        String classList = namePrefix + ".list";
        String archiveName = namePrefix + ".jsa";

        // dump class list
        CDSTestUtils.dumpClassList(classList, "-cp", appJar, mainClass);

        // create archive with the class list
        CDSOptions opts = (new CDSOptions())
            .addPrefix("-XX:ExtraSharedClassListFile=" + classList,
                       "-cp", appJar,
                       "-Xlog:cds,cds+class=debug")
            .setArchiveName(archiveName);
        CDSTestUtils.createArchiveAndCheck(opts);

        // run with archive; make sure the lambda is loaded from the archive
        CDSOptions runOpts = (new CDSOptions())
            .addPrefix("-cp", appJar, "-Xlog:class+load")
            .setArchiveName(archiveName)
            .setUseVersion(false)
            .addSuffix(mainClass);
        OutputAnalyzer output = CDSTestUtils.runWithArchive(runOpts);
        output.shouldMatch("LambdaInvokeVirtualApp[$][$]Lambda/.*source: shared objects file");
        output.shouldHaveExitValue(0);
    }
}

interface MyFunctionalInterface {
    Object invokeMethodReference(String s, char c1, char c2) throws Throwable;
}

class LambdaInvokeVirtualApp {
    private static MethodHandle createMethodHandle() throws NoSuchMethodException, IllegalAccessException {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(String.class, char.class, char.class);
        return lookup.findVirtual(String.class, "replace", mt);
    }

    public static void main(String argv[]) throws Throwable {
        MethodHandle ms = createMethodHandle();
        MyFunctionalInterface instance = ms::invoke;

/*
        The above is compiled into this bytecode. Note that the MethodHandle is of type REF_invokeVirtual

        invokedynamic    InvokeDynamic REF_invokeStatic:Method java/lang/invoke/LambdaMetafactory.metafactory:"(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodType;Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;":invokeMethodReference:"(Ljava/lang/invoke/MethodHandle;)LMyFunctionalInterface;" {
            MethodType "(Ljava/lang/String;CC)Ljava/lang/Object;",
            MethodHandle REF_invokeVirtual:Method java/lang/invoke/MethodHandle.invoke:"(Ljava/lang/String;CC)Ljava/lang/Object;",
            MethodType "(Ljava/lang/String;CC)Ljava/lang/Object;"
        };

*/

        Object result = instance.invokeMethodReference("some string to search", 's', 'o');
        String expected = "oome otring to oearch";
        if (!result.equals(expected)) {
            throw new RuntimeException("Expected \"" + expected + "\" but got \"" +
                                       result + "\"");
        }
    }
}
