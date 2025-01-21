/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package p1;

public class Holder {
    public Private_PublicClass             c1 = new Private_PublicClass();
    public Private_PublicInterface         c2 = new Private_PublicInterface();
    public Private_PrivateInterface1       c3 = new Private_PrivateInterface1();
    public Private_PrivateInterface2       c4 = new Private_PrivateInterface2();

    public Public_PublicClass              c5 = new Public_PublicClass();
    public Public_PublicInterface          c6 = new Public_PublicInterface();
    public Public_PrivateInterface1        c7 = new Public_PrivateInterface1();
    public Public_PrivateInterface2        c8 = new Public_PrivateInterface2();

    public Private_PublicClass[]          ac1 = new Private_PublicClass[0];
    public Private_PublicInterface[]      ac2 = new Private_PublicInterface[0];
    public Private_PrivateInterface1[]    ac3 = new Private_PrivateInterface1[0];
    public Private_PrivateInterface2[]    ac4 = new Private_PrivateInterface2[0];

    public Public_PublicClass[]           ac5 = new Public_PublicClass[0];
    public Public_PublicInterface[]       ac6 = new Public_PublicInterface[0];
    public Public_PrivateInterface1[]     ac7 = new Public_PrivateInterface1[0];
    public Public_PrivateInterface2[]     ac8 = new Public_PrivateInterface2[0];

    public Private_PublicClass[][]       aac1 = new Private_PublicClass[0][];
    public Private_PublicInterface[][]   aac2 = new Private_PublicInterface[0][];
    public Private_PrivateInterface1[][] aac3 = new Private_PrivateInterface1[0][];
    public Private_PrivateInterface2[][] aac4 = new Private_PrivateInterface2[0][];

    public Public_PublicClass[][]        aac5 = new Public_PublicClass[0][];
    public Public_PublicInterface[][]    aac6 = new Public_PublicInterface[0][];
    public Public_PrivateInterface1[][]  aac7 = new Public_PrivateInterface1[0][];
    public Public_PrivateInterface2[][]  aac8 = new Public_PrivateInterface2[0][];

    public PublicInterface                 i1 = new Private_PublicInterface();
    public PrivateInterface1               i2 = new Private_PrivateInterface1();
    public PrivateInterface2               i3 = new Private_PrivateInterface2();

    public PublicInterface[]              ai1 = new Private_PublicInterface[0];
    public PrivateInterface1[]            ai2 = new Private_PrivateInterface1[0];
    public PrivateInterface2[]            ai3 = new Private_PrivateInterface2[0];

    public PublicInterface[][]           aai1 = new Private_PublicInterface[0][];
    public PrivateInterface1[][]         aai2 = new Private_PrivateInterface1[0][];
    public PrivateInterface2[][]         aai3 = new Private_PrivateInterface2[0][];
}

interface PrivateInterface1 {
}

interface PrivateInterface2 extends PublicInterface {
}

class Private_PublicClass extends PublicClass {
    public String toString() {
        return "passed";
    }
}

class Private_PublicInterface implements PublicInterface {
    public String toString() {
        return "passed";
    }
}

class Private_PrivateInterface1 implements PrivateInterface1 {
    public String toString() {
        return "passed";
    }
}

class Private_PrivateInterface2 implements PrivateInterface2 {
    public String toString() {
        return "passed";
    }
}
