/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

class T8262095 {

    void f(Stream<Entry<Long, List<String>>> stream) {
        stream.sorted(Entry.comparingByKey()
                           .thenComparing((Map.Entry<Long, List<String>> e) -> e.getValue().hashCode()))
              .count();
    }
}
