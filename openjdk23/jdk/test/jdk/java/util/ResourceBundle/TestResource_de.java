/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

// NOTE:  This class is part of the ResourceBundleTest.

// NOTE:  As of 11/21/97, this class isn't actually being used by the ResourceBundleTest
// anymore.  I've left it here simply because we don't currently have a way to obsolete
// a file in RCS.  If you're running it, you've got a problem!

import java.util.*;
import java.io.*;

public class TestResource_de extends PropertyResourceBundle {
    public TestResource_de() throws IOException, FileNotFoundException {
        super(new FileInputStream("TestResource_de"));
    }
}
