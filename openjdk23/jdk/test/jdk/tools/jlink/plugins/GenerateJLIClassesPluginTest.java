/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import static java.lang.constant.ConstantDescs.CD_Object;
import static java.lang.constant.ConstantDescs.CD_int;

import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.lang.constant.MethodTypeDesc;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import jdk.tools.jlink.internal.LinkableRuntimeImage;
import tests.Helper;
import tests.JImageGenerator;
import tests.JImageValidator;
import tests.Result;


/*
 * @test
 * @bug 8252919 8327499
 * @library ../../lib
 * @summary Test --generate-jli-classes plugin
 * @modules java.base/jdk.internal.jimage
 *          jdk.jlink/jdk.tools.jlink.internal
 *          jdk.jlink/jdk.tools.jlink.internal.plugins
 *          jdk.jlink/jdk.tools.jmod
 *          jdk.jlink/jdk.tools.jimage
 * @build tests.*
 * @run testng/othervm GenerateJLIClassesPluginTest
 */
public class GenerateJLIClassesPluginTest {

    private static Helper helper;

    @BeforeTest
    public static void setup() throws Exception {
        boolean isLinkableRuntime = LinkableRuntimeImage.isLinkableRuntime();
        System.out.println("DEBUG: Tests run on " +
                           (isLinkableRuntime ? "enabled" : "disabled") +
                           " capability of linking from the run-time image.");
        System.out.println("DEBUG: default module-path, 'jmods', " +
                           (Helper.jdkHasPackagedModules() ? "" : "NOT ") +
                           "present.");
        helper = Helper.newHelper(isLinkableRuntime);
        if (helper == null) {
            // In case of no linkable run-time image and also no packaged
            // modules, helper will be null.
            System.err.println("Test not run");
            return;
        }
    }

    @Test
    public static void testSpecies()  throws IOException {
        // Check that --generate-jli-classes=@file works as intended
        Path baseFile = Files.createTempFile("base", "trace");
        String species = "LLLLLLLLLLLLLLLLLLL";
        String fileString = "[SPECIES_RESOLVE] java.lang.invoke.BoundMethodHandle$Species_" + species + " (salvaged)\n";
        Files.write(baseFile, fileString.getBytes(Charset.defaultCharset()));
        Result result = JImageGenerator.getJLinkTask()
                .output(helper.createNewImageDir("generate-jli-file"))
                .option("--generate-jli-classes=@" + baseFile.toString())
                .addMods("java.base")
                .call();

        Path image = result.assertSuccess();
        validateHolderClasses(image);
        JImageValidator.validate(image.resolve("lib").resolve("modules"),
                classFilesForSpecies(List.of(species)), // species should be in the image
                classFilesForSpecies(List.of(species.substring(1)))); // but not it's immediate parent
    }

    @Test
    public static void testInvalidSignatures() throws IOException {
        // Check that --generate-jli-classes=@file fails as intended on shapes that can't be generated
        String[] args = new String[] {
                "[LF_RESOLVE] java.lang.invoke.DirectMethodHandle$Holder invokeVirtual L_L (success)\n",
                "[LF_RESOLVE] java.lang.invoke.DirectMethodHandle$Holder invokeInterface L_L (success)\n",
                "[LF_RESOLVE] java.lang.invoke.DirectMethodHandle$Holder invokeStatic I_L (success)\n"
        };
        for (String fileString : args) {
            Path failFile = Files.createTempFile("fail", "trace");
            fileString = "[LF_RESOLVE] java.lang.invoke.DirectMethodHandle$Holder invokeVirtual L_L (success)\n";
            Files.write(failFile, fileString.getBytes(Charset.defaultCharset()));
            Result result = JImageGenerator.getJLinkTask()
                    .output(helper.createNewImageDir("invalid-signature"))
                    .option("--generate-jli-classes=@" + failFile.toString())
                    .addMods("java.base")
                    .call();

            result.assertFailure();
        }
    }

    @Test
    public static void nonExistentTraceFile() throws IOException {
        Result result = JImageGenerator.getJLinkTask()
                .output(helper.createNewImageDir("non-existent-tracefile"))
                .option("--generate-jli-classes=@NON_EXISTENT_FILE")
                .addMods("java.base")
                .call();

        Path image = result.assertSuccess();
        validateHolderClasses(image);
    }

    @Test
    public static void testInvokers() throws IOException {
        var fileString = "[LF_RESOLVE] java.lang.invoke.Invokers$Holder invoker L3I_L (fail)";
        Path invokersTrace = Files.createTempFile("invokers", "trace");
        Files.writeString(invokersTrace, fileString, Charset.defaultCharset());
        Result result = JImageGenerator.getJLinkTask()
                .output(helper.createNewImageDir("jli-invokers"))
                .option("--generate-jli-classes=@" + invokersTrace.toString())
                .addMods("java.base")
                .call();

        var image = result.assertSuccess();
        var targetMtd = MethodTypeDesc.of(CD_Object, CD_Object, CD_Object, CD_Object, CD_int);

        validateHolderClasses(image);
        JImageValidator.validate(image.resolve("lib").resolve("modules"),
                List.of(), List.of(), bytes -> {
                    var cf = ClassFile.of().parse(bytes);
                    if (!cf.thisClass().name().equalsString("java/lang/invoke/Invokers$Holder")) {
                        return;
                    }

                    boolean found = false;
                    for (var m : cf.methods()) {
                        // LambdaForm.Kind
                        if (m.methodName().equalsString("invoker") && m.methodTypeSymbol().equals(targetMtd)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        var methodsInfo = cf.methods().stream()
                                .map(m -> m.methodName() + m.methodTypeSymbol().displayDescriptor())
                                .collect(Collectors.joining("\n"));

                        Assert.fail("Missing invoker L3I_L in java.lang.invoke.Invokers$Holder, found:\n" + methodsInfo);
                    }
                });
    }

    private static void validateHolderClasses(Path image) throws IOException {
        JImageValidator.validate(image.resolve("lib").resolve("modules"),
                List.of("/java.base/java/lang/invoke/DirectMethodHandle$Holder.class",
                        "/java.base/java/lang/invoke/DelegatingMethodHandle$Holder.class",
                        "/java.base/java/lang/invoke/LambdaForm$Holder.class",
                        "/java.base/java/lang/invoke/Invokers$Holder.class"),
                List.of());
    }

    private static List<String> classFilesForSpecies(Collection<String> species) {
        return species.stream()
                .map(s -> "/java.base/java/lang/invoke/BoundMethodHandle$Species_" + s + ".class")
                .collect(Collectors.toList());
    }

}
