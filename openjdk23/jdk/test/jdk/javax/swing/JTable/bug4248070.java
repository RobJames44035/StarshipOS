/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
   @test
   @bug 4248070
   @summary cellEditor bound in JTable.
*/

import javax.swing.JTable;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

public class bug4248070 {

  public static void main(String[] argv) {

    BeanInfo bi = null;

    try {
        bi = Introspector.getBeanInfo(JTable.class);
    } catch (IntrospectionException e) {
    }

    PropertyDescriptor[] pd = bi.getPropertyDescriptors();
    int i;
    for (i=0; i<pd.length; i++) {
        if (pd[i].getName().equals("cellEditor")) {
            break;
        }
    }
    if (!pd[i].isBound()) {
       throw new RuntimeException("cellEditor property of JTable isn't flagged as bound in bean info...");
    }
  }

}
