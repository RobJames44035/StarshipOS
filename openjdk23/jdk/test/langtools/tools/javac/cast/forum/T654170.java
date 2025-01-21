/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     5066774
 * @summary Bad interaction between generics, instanceof, inner classes, and subclasses
 * @author  RGibson
 * @compile -Werror T654170.java
 */

// http://forum.java.sun.com/thread.jspa?forumID=316&threadID=654170

import java.util.*;

public class T654170 {
    public static void main(String[] args) {
        Base<?> b = new Sub ();
        if (b instanceof Sub) {
        }
    }
}

class Base<T extends List> {}

class Sub extends Base<ArrayList> {}
