/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package resources;

import java.util.ListResourceBundle;


 public class TableRowTest extends ListResourceBundle {
  public Object[][] getContents() {
      return contents;
  }

  // LOCALIZE THIS
  // note: probably don't need to localize integers and booleans
  static Object[][] data = {
      {"Mark", "Andrews", "Red", Integer.valueOf(2), Boolean.valueOf(true)},
      {"Tom", "Ball", "Blue", Integer.valueOf(99), Boolean.valueOf(false)},
      {"Alan", "Chung", "Green", Integer.valueOf(838), Boolean.valueOf(false)},
      {"Jeff", "Dinkins", "Turquois", Integer.valueOf(8), Boolean.valueOf(true)},
      {"Amy", "Fowler", "Yellow", Integer.valueOf(3), Boolean.valueOf(false)},
      {"Brian", "Gerhold", "Green", Integer.valueOf(0), Boolean.valueOf(false)},
      {"James", "Gosling", "Pink", Integer.valueOf(21), Boolean.valueOf(false)},
      {"David", "Karlton", "Red", Integer.valueOf(1), Boolean.valueOf(false)},
      {"Dave", "Kloba", "Yellow", Integer.valueOf(14), Boolean.valueOf(false)},
      {"Peter", "Korn", "Purple", Integer.valueOf(12), Boolean.valueOf(false)},
      {"Phil", "Milne", "Purple", Integer.valueOf(3), Boolean.valueOf(false)},
      {"Dave", "Moore", "Green", Integer.valueOf(88), Boolean.valueOf(false)},
      {"Hans", "Muller", "Maroon", Integer.valueOf(5), Boolean.valueOf(false)},
      {"Rick", "Levenson", "Blue", Integer.valueOf(2), Boolean.valueOf(false)},
      {"Tim", "Prinzing", "Blue", Integer.valueOf(22), Boolean.valueOf(false)},
      {"Chester", "Rose", "Black", Integer.valueOf(0), Boolean.valueOf(false)},
      {"Ray", "Ryan", "Gray", Integer.valueOf(77), Boolean.valueOf(false)},
      {"Georges", "Saab", "Red", Integer.valueOf(4), Boolean.valueOf(false)},
      {"Willie", "Walker", "Phthalo Blue", Integer.valueOf(4), Boolean.valueOf(false)},
      {"Kathy", "Walrath", "Blue", Integer.valueOf(8), Boolean.valueOf(false)},
      {"Arnaud", "Weber", "Green", Integer.valueOf(44), Boolean.valueOf(false)},
      {"Mark", "Andrews", "Red", Integer.valueOf(2), Boolean.valueOf(true)},
      {"Tom", "Ball", "Blue", Integer.valueOf(99), Boolean.valueOf(false)},
      {"Alan", "Chung", "Green", Integer.valueOf(838), Boolean.valueOf(false)},
      {"Jeff", "Dinkins", "Turquois", Integer.valueOf(8), Boolean.valueOf(true)},
      {"Amy", "Fowler", "Yellow", Integer.valueOf(3), Boolean.valueOf(false)},
      {"Brian", "Gerhold", "Green", Integer.valueOf(0), Boolean.valueOf(false)},
      {"James", "Gosling", "Pink", Integer.valueOf(21), Boolean.valueOf(false)},
      {"David", "Karlton", "Red", Integer.valueOf(1), Boolean.valueOf(false)},
      {"Dave", "Kloba", "Yellow", Integer.valueOf(14), Boolean.valueOf(false)},
      {"Peter", "Korn", "Purple", Integer.valueOf(12), Boolean.valueOf(false)},
      {"Phil", "Milne", "Purple", Integer.valueOf(3), Boolean.valueOf(false)},
      {"Dave", "Moore", "Green", Integer.valueOf(88), Boolean.valueOf(false)},
      {"Hans", "Muller", "Maroon", Integer.valueOf(5), Boolean.valueOf(false)},
      {"Rick", "Levenson", "Blue", Integer.valueOf(2), Boolean.valueOf(false)},
      {"Tim", "Prinzing", "Blue", Integer.valueOf(22), Boolean.valueOf(false)},
      {"Chester", "Rose", "Black", Integer.valueOf(0), Boolean.valueOf(false)},
      {"Ray", "Ryan", "Gray", Integer.valueOf(77), Boolean.valueOf(false)},
      {"Georges", "Saab", "Red", Integer.valueOf(4), Boolean.valueOf(false)},
      {"Willie", "Walker", "Phthalo Blue", Integer.valueOf(4), Boolean.valueOf(false)},
      {"Kathy", "Walrath", "Blue", Integer.valueOf(8), Boolean.valueOf(false)},
      {"Arnaud", "Weber", "Green", Integer.valueOf(44), Boolean.valueOf(false)}
  };
  // END OF MATERIAL TO LOCALIZE

  static final Object[][] contents = {
      {"TableData", data }        // array for table data
  };
 }

