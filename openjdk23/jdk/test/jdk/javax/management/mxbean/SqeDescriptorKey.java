/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.management.DescriptorKey;

/**
 * That annotation is usable everywhere DescriptorKey is (and even more).
 * It is for use to test that you can retrieve the SqeDescriptorKey into the
 * appropriate Descriptor instances as built by the JMX runtime.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SqeDescriptorKey {
    @DescriptorKey("sqeDescriptorKey")
    String value();

    // List descriptor fields that may be added or may be updated
    // when retrieving an MBeanInfo using a JMXWS connection compared to the
    // MBeanInfo returned by a local MBeanServer.
    // The annotation format is :
    //   <descriptorFieldName>=<descriptorFieldValue>
    // The values actually handled by the test suite are :
    //   openType=SimpleType.VOID
    @DescriptorKey("descriptorFields")
    String[] descriptorFields() default {};
}
