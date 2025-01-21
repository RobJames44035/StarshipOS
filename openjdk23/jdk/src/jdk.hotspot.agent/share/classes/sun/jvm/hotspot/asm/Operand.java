/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.jvm.hotspot.asm;

import sun.jvm.hotspot.utilities.Assert;

/*
 * Operand is used as RTL argument. An Operand is either
 * a Number or a Register or an Address.
*/

public abstract class Operand {
   // few type testers
   public boolean isAddress() {
      return false;
   }

   public boolean isImmediate() {
      return false;
   }

   public boolean isRegister() {
      return false;
   }
}
