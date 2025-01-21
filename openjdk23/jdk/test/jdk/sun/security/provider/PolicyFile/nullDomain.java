/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 *
 *
 *
 * @test
 * @bug 4507434
 * @summary PolicyFile.getPermissions(ProtectionDomain) should return
 *           empty permissions when passed in null protection domain
 *
 */

import java.security.*;
import java.util.*;

public class nullDomain {
      public static void main(String[] args) {
      try {
          System.out.println(
            Policy.getPolicy().getPermissions((ProtectionDomain) null));
          PropertyPermission p = new PropertyPermission
                                        ("user.home","read");
          if (Policy.getPolicy().implies((ProtectionDomain)null, p))
                System.out.println ("implies succeeded");
       } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException ("Unexpected exception " +
                                             e.toString());

       }
     }
}
