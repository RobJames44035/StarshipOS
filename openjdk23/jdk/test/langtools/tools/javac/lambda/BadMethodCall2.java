/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
class BadMethodCall2 {
     void test(Object rec) {
         rec.nonExistent(System.out::println);
         rec.nonExistent(()->{});
         rec.nonExistent(true ? "1" : "2");
     }
}
