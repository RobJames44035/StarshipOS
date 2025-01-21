/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

package org.openjdk.tests.separate;

import java.io.*;

public class AttributeInjector implements ClassFilePreprocessor {

    private String attributeName;
    private byte[] attributeData;

    public AttributeInjector(String attributeName, byte[] attributeData) {
        this.attributeName = attributeName;
        this.attributeData = attributeData;
    }

    public byte[] preprocess(String name, byte[] cf) {
        ClassFile classfile = new ClassFile(cf);

        short cpIndex = (short)classfile.constant_pool.size();

        ClassFile.CpUtf8 entry = new ClassFile.CpUtf8();
        entry.bytes = new byte[attributeName.length()];
        for (int i = 0; i < attributeName.length(); ++i) {
            entry.bytes[i] = (byte)attributeName.charAt(i);
        }

        classfile.constant_pool.add(entry);

        ClassFile.Attribute attr = new ClassFile.Attribute();
        attr.attribute_name_index = cpIndex;
        attr.info = attributeData;

        classfile.attributes.add(attr);
        return classfile.toByteArray();
    }

/*
    public static void main(String argv[]) throws Exception {
        File input = new File(argv[0]);
        byte[] buffer = new byte[(int)input.length()];
        new FileInputStream(input).read(buffer);

        ClassFilePreprocessor cfp =
            new AttributeInjector("RequiresBridges", new byte[0]);
        byte[] cf = cfp.preprocess(argv[0], buffer);
        new FileOutputStream(argv[0] + ".mod").write(cf);
    }
*/
}
