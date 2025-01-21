/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @summary Test of method selection and resolution cases that
 * generate IncompatibleClassChangeError
 * @requires vm.opt.final.ClassUnloading
 * @library /testlibrary/asm
 * @library /runtime/SelectionResolution/classes
 * @run main/othervm/timeout=1200 -XX:+IgnoreUnrecognizedVMOptions -XX:-VerifyDependencies InvokeVirtualICCE
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

public class InvokeVirtualICCE extends SelectionResolutionTest {

    private static final SelectionResolutionTestCase.Builder initBuilder =
        new SelectionResolutionTestCase.Builder();

    static {
        initBuilder.setResult(Result.ICCE);
    }

    private static final Collection<TestGroup> testgroups =
        Arrays.asList(
                /* invokevirtual tests */

                /* resolved method is static*/
                /* Group 161: callsite = methodref = expected */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefEqualsExpected,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.MethodrefSelectionResolvedIsClass),
                /* Group 162: callsite = methodref, methodref != expected,
                 * expected is class, expected and callsite in the same package
                 */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.CLASS),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PACKAGE,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedClass,
                        Template.MethodrefNotEqualsExpectedClass,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.MethodrefSelectionResolvedIsClass),
                /* Group 163: callsite = methodref, methodref != expected,
                 * expected is interface, expected and callsite in the same package
                 */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.of(MethodData.Context.STATIC),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefNotEqualsExpectedIface,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.MethodrefSelectionResolvedIsIface),

                /* methodref is an interface */
                /* Group 164: callsite = methodref = expected */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.allOf(MethodData.Context.class),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefEqualsExpected,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.IfaceMethodrefSelection),
                /* Group 165: callsite = methodref, methodref != expected,
                 * expected and callsite in the same package
                 */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC,
                                                        MethodData.Access.PRIVATE),
                                             EnumSet.allOf(MethodData.Context.class),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.IfaceMethodrefNotEqualsExpected,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.IfaceMethodrefSelection),

                /* Group 166: Ambiguous resolution tests */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC),
                                             EnumSet.allOf(MethodData.Context.class),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefAmbiguous,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.MethodrefSelectionResolvedIsIfaceNoOverride),
                /* Group 167: ambiguous selection */
                new TestGroup.Simple(initBuilder,
                        Template.SetInvoke(SelectionResolutionTestCase.InvokeInstruction.INVOKEVIRTUAL),
                        Template.ResultCombo(EnumSet.of(Template.Kind.INTERFACE),
                                             EnumSet.of(MethodData.Access.PUBLIC),
                                             EnumSet.of(MethodData.Context.INSTANCE),
                                             EnumSet.of(ClassData.Package.SAME,
                                                        ClassData.Package.DIFFERENT)),
                        Template.OverrideAbstractExpectedIface,
                        Template.MethodrefNotEqualsExpectedIface,
                        Template.IgnoredAbstract,
                        Template.AllCallsiteCases,
                        Template.MethodrefAmbiguousResolvedIsIface)
                );

    private InvokeVirtualICCE() {
        super(testgroups);
    }

    public static void main(final String... args) {
        new InvokeVirtualICCE().run();
    }
}
