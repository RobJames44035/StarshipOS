/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4881292
 * @summary compiler crash in class writer
 * @author gafter
 *
 * @compile  Crash02.java
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class Bug2<T> implements Iterable<T> {
   private List<T> data;

   public Bug2() {
      data = new ArrayList<T>();
   }

   public Iterator<T> iterator() {
      return data.iterator();
   }
}
