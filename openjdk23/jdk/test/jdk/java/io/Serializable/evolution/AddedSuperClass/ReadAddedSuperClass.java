/*
 * StarshipOS Copyright (c) 1997-2025. R.A. James
 */

/*
 *  @bug 4070080
 *
 *  @build WriteAddedSuperClass ReadAddedSuperClass
 *  @run main ReadAddedSuperClass
 *  @summary Test reading an evolved class serialization into the original class
 *           version. Class evolved by adding a superclass.
 *           This is a compatible class evolution.
 *
 *  Part a of test serializes an instance of an evolved class to a serialization stream.
 *  Part b of test deserializes the serialization stream into an instance of
 *  the original class.
 *
 *  Description of failure:
 *
 *  If you delete AddedSuperClass.class before running this test,
 *  both JDK 1.1.x and 1.2 result in ClassNotFoundException for class
 *  AddedSuperClass.  If the .class file is not deleted, 1.2 does not
 *  fail. However, JDK 1.1.4 core dumps dereferencing a null class handle
 *  in the native method for inputClassDescriptor.
 */

import java.io.*;

class A implements Serializable {
    // Version 1.0 of class A.
    private static final long serialVersionUID = 1L;
}

public class ReadAddedSuperClass {
    public static void main(String args[]) throws IOException, ClassNotFoundException {
        File f = new File("tmp.ser");
        ObjectInput in =
            new ObjectInputStream(new FileInputStream(f));
        A a = (A)in.readObject();
        in.close();
    }
}
