/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test TestConjointAtomicArraycopy
 * @bug 7100935
 * @summary verify that oops are copied element-wise atomic
 * @run main/othervm -Xint TestConjointAtomicArraycopy
 * @run main/othervm -Xcomp -Xbatch TestConjointAtomicArraycopy
 * @author axel.siebenborn@sap.com
 */

public class TestConjointAtomicArraycopy {

  static volatile Object [] testArray = new Object [4];

  static short[] a1 = new short[8];
  static short[] a2 = new short[8];
  static short[] a3 = new short[8];

  static volatile boolean keepRunning = true;

  static void testOopsCopy() throws InterruptedException{

  }

  public static void main(String[] args ) throws InterruptedException{
    for (int i = 0; i < testArray.length; i++){
      testArray[i] = new String("A");
    }

    Thread writer = new Thread (new Runnable(){
      public void run(){
        for (int i = 0 ; i < 1000000; i++) {
          System.arraycopy(testArray, 1, testArray, 0, 3);
          testArray[2] = new String("a");
        }
      }
    });

    Thread reader = new Thread( new Runnable(){
      public void run(){
        while (keepRunning){
          String name = testArray[2].getClass().getName();
          if(!(name.endsWith("String"))){
            throw new RuntimeException("got wrong class name");
          }
        }
      }
    });
    keepRunning = true;
    reader.start();
    writer.start();
    writer.join();
    keepRunning = false;
    reader.join();
  }
}
