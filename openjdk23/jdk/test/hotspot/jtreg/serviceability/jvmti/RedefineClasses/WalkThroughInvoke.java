/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.lang.reflect.*;

public class WalkThroughInvoke {
  public void stackWalk() {
      try {
          Class b = Object.class;
          SecurityManager sm = new SecurityManager();
          // Walks the stack with Method.invoke in the stack (which is the
          // purpose of the test) before it gets an AccessControlException.
          sm.checkPermission(new RuntimePermission("accessDeclaredMembers"));
      } catch (java.security.AccessControlException e) {
          // Ignoring an 'AccessControlException' exception since
          // it is expected as part of this test.
      }
  }
};
