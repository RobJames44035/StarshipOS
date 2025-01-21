/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 4248694
 * @modules jdk.localedata
 * @summary updating collation tables for icelandic
 */

import java.text.Collator;
import java.util.Arrays;
import java.util.Locale;

public class Bug4248694 {

  /********************************************************
  *********************************************************/
  public static void main (String[] args) {
      Locale reservedLocale = Locale.getDefault();
      try {
          int errors=0;

          Locale loc = new Locale ("is", "is");   // Icelandic

          Locale.setDefault (loc);
          Collator col = Collator.getInstance ();

          String[] data = {"\u00e6ard",
                           "Zard",
                           "aard",
                           "\u00feard",
                           "vird",
                           "\u00c6ard",
                           "Zerd",
                           "\u00deard"};

          String[] sortedData = {"aard",
                                 "vird",
                                 "Zard",
                                 "Zerd",
                                 "\u00feard",
                                 "\u00deard",
                                 "\u00e6ard",
                                 "\u00c6ard"};

          Arrays.sort (data, col);

          System.out.println ("Using " + loc.getDisplayName());
          for (int i = 0;  i < data.length;  i++) {
              System.out.println(data[i] + "  :  " + sortedData[i]);
              if (sortedData[i].compareTo(data[i]) != 0) {
                  errors++;
              }
          }//end for

          if (errors > 0)
              throw new RuntimeException();
      } finally {
          // restore the reserved locale
          Locale.setDefault(reservedLocale);
      }
  }//end main

}//end class CollatorTest
