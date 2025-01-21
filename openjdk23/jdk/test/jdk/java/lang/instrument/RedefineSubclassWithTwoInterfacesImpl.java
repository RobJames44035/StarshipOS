/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

// Reproducing this bug only requires an EMCP version of the
// RedefineSubclassWithTwoInterfacesImpl class so
// RedefineSubclassWithTwoInterfacesImpl.java and
// RedefineSubclassWithTwoInterfacesImpl_1.java are identical.
public class RedefineSubclassWithTwoInterfacesImpl
                 extends RedefineSubclassWithTwoInterfacesTarget
                 implements RedefineSubclassWithTwoInterfacesIntf1,
                            RedefineSubclassWithTwoInterfacesIntf2 {
    // This class is acting in the role of:
    // wlstest.unit.diagnostics.common.apps.echoejb.EchoBean4_nh7k_Impl
}
