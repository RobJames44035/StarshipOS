/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import java.util.*;
import java.io.*;

// Delegating class loader which never defines a class.
public class ParentClassLoader extends ClassLoader {
    public ParentClassLoader() { super(); }
    public ParentClassLoader(String name, ClassLoader l) { super(name, l); }
}
