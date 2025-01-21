/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug 6305139
 * @summary Check that calling setFields with a field names array with a
 * null name in it and calling setFields with a field names array with an
 * empty name in it throw the expected exceptions.
 * @author Luis-Miguel Alventosa
 *
 * @run clean ImmutableDescriptorSetFieldsTest
 * @run build ImmutableDescriptorSetFieldsTest
 * @run main ImmutableDescriptorSetFieldsTest
 */

import javax.management.ImmutableDescriptor;
import javax.management.RuntimeOperationsException;

public class ImmutableDescriptorSetFieldsTest {
    public static void main(String[] args) throws Exception {
        boolean ok = true;
        ImmutableDescriptor d = new ImmutableDescriptor("k=v");
        try {
            System.out.println(
                "Call ImmutableDescriptor.setFields(fieldNames,fieldValues) " +
                "with empty name in field names array");
            String fieldNames[] = { "a", "", "c" };
            Object fieldValues[] = { 1, 2, 3 };
            d.setFields(fieldNames, fieldValues);
            System.out.println("Didn't get expected exception");
            ok = false;
        } catch (RuntimeOperationsException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                System.out.println("Got expected exception:");
                ok = true;
            } else {
                System.out.println("Got unexpected exception:");
                ok = false;
            }
            e.printStackTrace(System.out);
        } catch (Exception e) {
            System.out.println("Got unexpected exception:");
            ok = false;
            e.printStackTrace(System.out);
        }
        try {
            System.out.println(
                "Call ImmutableDescriptor.setFields(fieldNames,fieldValues) " +
                "with null name in field names array");
            String fieldNames[] = { "a", null, "c" };
            Object fieldValues[] = { 1, 2, 3 };
            d.setFields(fieldNames, fieldValues);
            System.out.println("Didn't get expected exception");
            ok = false;
        } catch (RuntimeOperationsException e) {
            if (e.getCause() instanceof IllegalArgumentException) {
                System.out.println("Got expected exception:");
                ok = true;
            } else {
                System.out.println("Got unexpected exception:");
                ok = false;
            }
            e.printStackTrace(System.out);
        } catch (Exception e) {
            System.out.println("Got unexpected exception:");
            ok = false;
            e.printStackTrace(System.out);
        }
        if (ok) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
            throw new Exception("Got unexpected exceptions");
        }
    }
}
