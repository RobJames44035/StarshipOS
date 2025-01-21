/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.security.ProtectionDomain;

// See ../AppCDSProtectionDomain.java
//
// ProtDomainB      is NOT stored in CDS archive.
// ProtDomainBOther is     stored in CDS archive.
//
// However, they should have the same ProtectionDomain instance.
public class ProtDomainB {
  public static void main(String args[]) {
    System.out.println("Testing ProtDomainB");
    ProtectionDomain mine = ProtDomainB.class.getProtectionDomain();
    ProtectionDomain his  = ProtDomainBOther.class.getProtectionDomain();

    System.out.println("mine = " + mine);
    System.out.println("his  = " + his);

    if (mine == his) {
      System.out.println("Protection Domains match");
    } else {
      throw new RuntimeException("Protection Domains do not match!");
    }
  }
}

class ProtDomainBOther {

}
