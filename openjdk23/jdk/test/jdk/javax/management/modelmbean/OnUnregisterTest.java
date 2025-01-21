/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6175387
 * @summary Check that OnUnregister is an allowed value for persistPolicy
 * in ModelMBeanAttributeInfo
 * @author Eamonn McManus
 *
 * @run clean OnUnregisterTest
 * @run build OnUnregisterTest
 * @run main OnUnregisterTest
 */

// Since our RequiredModelMBean implementation doesn't support
// persistence, it doesn't have any behaviour for OnUnregister, so we
// can't test that.  We can only test that the value is allowed.

// In versions of the API prior to the addition of OnUnregister,  the
// attempt to construct a DescriptorSupport with persistPolicy=OnUnregister
// will throw an exception.

// The OnUnregister value is not case-sensitive, and we test that.

import javax.management.*;
import javax.management.modelmbean.*;

public class OnUnregisterTest {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = MBeanServerFactory.newMBeanServer();
        ObjectName on = new ObjectName("a:b=c");

        DescriptorSupport desc;
        ModelMBeanAttributeInfo mmbai;
        ModelMBeanInfo mmbi;
        ModelMBean mmb;

        desc = new DescriptorSupport("name=foo",
                                     "descriptorType=attribute",
                                     "persistPolicy=OnUnregister");
        mmbai = new ModelMBeanAttributeInfo("foo", "int", "a foo",
                                            true, true, false, desc);
        mmbi = new ModelMBeanInfoSupport("a.b.c", "description",
                                         new ModelMBeanAttributeInfo[] {mmbai},
                                         null, null, null);
        mmb = new RequiredModelMBean(mmbi);

        mbs.registerMBean(mmb, on);
        mbs.unregisterMBean(on);

        desc = new DescriptorSupport("name=foo", "descriptorType=attribute");
        mmbai = new ModelMBeanAttributeInfo("foo", "int", "a foo",
                                            true, true, false, desc);
        desc = new DescriptorSupport("name=bar",
                                     "descriptorType=mbean",
                                     "persistPolicy=onUnregister");
        mmbi = new ModelMBeanInfoSupport("a.b.c", "description",
                                         new ModelMBeanAttributeInfo[] {mmbai},
                                         null, null, null, desc);
        mmb = new RequiredModelMBean(mmbi);
        mbs.registerMBean(mmb, on);
        mbs.unregisterMBean(on);
    }
}
