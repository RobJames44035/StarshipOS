/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Test of invokestatic method selection and resolution cases that
 * generate IncompatibleClassChangeError
 * @library /testlibrary/asm
 * @library /runtime/SelectionResolution/classes
 * @run main/othervm -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies InvokeStaticICCE
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import selectionresolution.ClassData;
import selectionresolution.MethodData;
import selectionresolution.Result;
import selectionresolution.SelectionResolutionTest;
import selectionresolution.SelectionResolutionTestCase;
import selectionresolution.Template;

public class InvokeStaticICCE extends SelectionResolutionTest {

    private static final SelectionResolutionTestCase.Builder initBuilder =
        new SelectionResolutionTestCase.Builder();

    static {
        initBuilder.setResult(Result.ICCE);
    }

    private static final Collection<TestGroup> testgroups =
        Arrays.asList(
                /* invokestatic tests */
                /* Group 157: methodref = expected */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESTATIC),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PROTECTED,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.INSTANCE,
                                                        MethodData.Context.ABSTRACT),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefEqualsExpected,
                        Template.AllCallsiteCases,
                        Template.TrivialObjectref),
                /* Group 158: methodref = expected, expected is interface */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESTATIC),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.INSTANCE,
                                                        MethodData.Context.ABSTRACT),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefEqualsExpected,
                        Template.AllCallsiteCases,
                        Template.TrivialObjectref),
                /* Group 159: methodref != expected, expected is class
                 */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESTATIC),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PROTECTED,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.INSTANCE,
                                                        MethodData.Context.ABSTRACT),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefNotEqualsExpectedClass,
                        Template.AllCallsiteCases,
                        Template.TrivialObjectref),
                /* Group 160: methodref = expected, expected is interface */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKESTATIC),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.INSTANCE,
                                                        MethodData.Context.ABSTRACT),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefNotEqualsExpectedIface,
                        Template.AllCallsiteCases,
                        Template.TrivialObjectref)
            );

    private InvokeStaticICCE() {
        super(testgroups);
    }

    public static void main(final String... args) {
        new InvokeStaticICCE().run();
    }
}
