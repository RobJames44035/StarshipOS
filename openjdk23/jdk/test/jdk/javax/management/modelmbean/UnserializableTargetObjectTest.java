/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6332962
 * @summary Test that a RequiredModelMBean operation can have a targetObject
 * that is not serializable
 * @author Eamonn McManus
 * @modules java.management.rmi
 * @run clean UnserializableTargetObjectTest
 * @run build UnserializableTargetObjectTest
 * @run main UnserializableTargetObjectTest
 */

/* This test and DescriptorSupportSerialTest basically cover the same thing.
   I wrote them at different times and forgot that I had written the earlier
   one.  However the coverage is slightly different so I'm keeping both.  */

import java.lang.reflect.Method;
import javax.management.Attribute;
import javax.management.Descriptor;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.modelmbean.DescriptorSupport;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.management.modelmbean.RequiredModelMBean;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class UnserializableTargetObjectTest {
    public static class Resource { // not serializable!
        int count;
        int operationCount;

        public void operation() {
            operationCount++;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = MBeanServerFactory.newMBeanServer();
        ObjectName name = new ObjectName("a:b=c");
        Resource resource1 = new Resource();
        Resource resource2 = new Resource();
        Resource resource3 = new Resource();
        Method operationMethod = Resource.class.getMethod("operation");
        Method getCountMethod = Resource.class.getMethod("getCount");
        Method setCountMethod = Resource.class.getMethod("setCount", int.class);
        Descriptor operationDescriptor =
            new DescriptorSupport(new String[] {
                                    "descriptorType", "name", "targetObject"
                                  }, new Object[] {
                                    "operation", "operation", resource1
                                  });
        Descriptor getCountDescriptor =
            new DescriptorSupport(new String[] {
                                    "descriptorType", "name", "targetObject"
                                  }, new Object[] {
                                    "operation", "getCount", resource2
                                  });
        Descriptor setCountDescriptor =
            new DescriptorSupport(new String[] {
                                    "descriptorType", "name", "targetObject"
                                  }, new Object[] {
                                    "operation", "setCount", resource2
                                  });
        Descriptor countDescriptor =
            new DescriptorSupport(new String[] {
                                    "descriptorType", "name", "getMethod", "setMethod"
                                  }, new Object[] {
                                    "attribute", "Count", "getCount", "setCount"
                                  });
        ModelMBeanOperationInfo operationInfo =
            new ModelMBeanOperationInfo("operation description",
                                        operationMethod, operationDescriptor);
        ModelMBeanOperationInfo getCountInfo =
            new ModelMBeanOperationInfo("getCount description",
                                        getCountMethod, getCountDescriptor);
        ModelMBeanOperationInfo setCountInfo =
            new ModelMBeanOperationInfo("setCount description",
                                        setCountMethod, setCountDescriptor);
        ModelMBeanAttributeInfo countInfo =
            new ModelMBeanAttributeInfo("Count", "Count description",
                                        getCountMethod, setCountMethod,
                                        countDescriptor);
        ModelMBeanInfo mmbi =
            new ModelMBeanInfoSupport(Resource.class.getName(),
                                      "ModelMBean to test targetObject",
                                      new ModelMBeanAttributeInfo[] {countInfo},
                                      null,  // no constructors
                                      new ModelMBeanOperationInfo[] {
                                          operationInfo, getCountInfo, setCountInfo
                                      },
                                      null); // no notifications
        ModelMBean mmb = new RequiredModelMBean(mmbi);
        mmb.setManagedResource(resource3, "ObjectReference");
        mbs.registerMBean(mmb, name);
        mbs.invoke(name, "operation", null, null);
        mbs.setAttribute(name, new Attribute("Count", 53));
        if (resource1.operationCount != 1)
            throw new Exception("operationCount: " + resource1.operationCount);
        if (resource2.count != 53)
            throw new Exception("count: " + resource2.count);
        int got = (Integer) mbs.getAttribute(name, "Count");
        if (got != 53)
            throw new Exception("got count: " + got);

        JMXServiceURL url = new JMXServiceURL("rmi", null, 0);
        JMXConnectorServer cs =
            JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        cs.start();
        JMXServiceURL addr = cs.getAddress();
        JMXConnector cc = JMXConnectorFactory.connect(addr);
        MBeanServerConnection mbsc = cc.getMBeanServerConnection();
        ModelMBeanInfo rmmbi = (ModelMBeanInfo) mbsc.getMBeanInfo(name);
        // Above gets NotSerializableException if resource included in
        // serialized form
        cc.close();
        cs.stop();
        System.out.println("TEST PASSED");
    }
}
