/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */


import java.io.PrintStream;
import java.util.*;

public class IsModifiableClassApp {

    public static void main(String args[]) throws Exception {
        (new IsModifiableClassApp()).run(args, System.out);
    }

    public void run(String args[], PrintStream out) throws Exception {
        if (!IsModifiableClassAgent.completed) {
            throw new Exception("ERROR: IsModifiableClassAgent did not complete.");
        }
        if (IsModifiableClassAgent.fail) {
            throw new Exception("ERROR: IsModifiableClass failed.");
        } else {
            out.println("IsModifiableClass succeeded.");
        }
    }
}
