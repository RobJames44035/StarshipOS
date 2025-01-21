/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */
public class MyOuter {
   public void exp() throws MyException {
       throw new MyException("MyOuter exception");
   }

   public void test() throws Exception {
       System.out.println("MyOuter");
       try {
          exp();
       } catch (MyException e) {
       }
   }

   public static final class MyInner extends MyOuter {
       static String myString = "shared_string_from_MyInner";
       public void test() {
           System.out.println("MyInner");
       }
   }
}
