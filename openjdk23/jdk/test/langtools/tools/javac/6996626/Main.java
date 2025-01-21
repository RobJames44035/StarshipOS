/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug 6996626
 * @summary Scope fix issues for ImportScope
 * @compile pack1/Symbol.java
 * @compile Main.java
 */

import pack1.*;
import pack1.Symbol.*;

// The following imports are just to trigger re-hashing (in
// com.sun.tools.javac.code.Scope.dble()) of the star-import scope.
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public void main (String[] args) {
        throw new CompletionFailure();
    }
}

