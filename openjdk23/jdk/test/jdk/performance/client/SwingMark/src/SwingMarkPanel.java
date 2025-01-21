/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Date;
import java.util.Vector;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
  * This class is the center point for running the automated
  * test suite.
  * It creates a number of instances of AbstractSwingTest. It
  * gets a component from each test and inserts it into a JTabbedPane.
  * It then sequentially selects each tab and runs the coresponding test
  */

public class SwingMarkPanel extends JTabbedPane {

   AbstractSwingTest[] tests;

   public SwingMarkPanel() {
      tests = getTests();
      for (int i = 0; i < tests.length; i++) {
         addTab(tests[i].getTestName(), tests[i].getTestComponent() );
      }
      setSelectedIndex(0);
   }

   /**
     * add new tests to the suite by adding objects
     * to the array returned by this function
     */
   @SuppressWarnings("deprecation")
   public AbstractSwingTest[] getTests() {

      Vector testVector = new Vector();
      try {

         String testList = "TestList.txt";

         FileReader file = null;
         LineNumberReader reader = null;

         try {
             file = new FileReader(testList);
             reader = new LineNumberReader(file);
         } catch (FileNotFoundException e) {
             InputStream is = getClass().getResourceAsStream("/resources/" + testList);
             reader = new LineNumberReader(new InputStreamReader(is));
         }

         String testName = reader.readLine();;

         while (testName != null ) {

            if (testName.indexOf("//") != 0) {

               try {
                  Class testClass = Class.forName(testName);
                  AbstractSwingTest test = (AbstractSwingTest)testClass.newInstance();
                  testVector.addElement(test);
               } catch (Exception e) {
                  System.out.println("Error instantiating test: " + testName);
                  System.out.println("Test must be subclass of AbstractSwingTest.");
                  e.printStackTrace();
               }
               testName = reader.readLine();
            }
         }
         reader.close();

      } catch (Exception e) {
         e.printStackTrace();
      }

      AbstractSwingTest[] tests = new AbstractSwingTest[testVector.size()];
      testVector.copyInto(tests);
      return tests;
   }

   /**
     * run each test and print the elapsed time
     */
   public void runTests(int runNumber) {
      TabSelecter selecter = new TabSelecter();
      for (int testNumber = 0; testNumber < tests.length; testNumber++) {
         selecter.setSelection(testNumber);
         try {
            // select the next tab
            SwingUtilities.invokeAndWait(selecter);
            AbstractSwingTest.rest();
         } catch (Exception e) {
            System.out.println(e);
         }
         Date start = new Date();  // mark the start time
         tests[testNumber].runTest();
         try {
            // wait for event queue to clear
            SwingUtilities.invokeAndWait(NullRunnable.singleton);
            AbstractSwingTest.syncRam();
            AbstractSwingTest.rest();
         } catch (Exception e) {
            System.out.println(e);
         }
         Date end = new Date();  // mark the completion time
         long elapsedTime = end.getTime() - start.getTime();

         Runtime runtime = Runtime.getRuntime();
         long heapSize = runtime.totalMemory();
         long freeMemory = runtime.freeMemory();
         long usedMemory = heapSize - freeMemory;
         SwingMark.memoryReport[runNumber][0] = usedMemory;
         SwingMark.memoryReport[runNumber][1] = heapSize;

         SwingMark.timeReport[runNumber][testNumber] = elapsedTime;

         System.out.println(tests[testNumber].getTestName() +
                            " = " + elapsedTime +
                            "   (Paint = " + tests[testNumber].getPaintCount() + ")");
      }
   }

   class TabSelecter implements Runnable {
      int selection;

      void setSelection(int tabToSelect) {
         selection = tabToSelect;
      }

      public void run() {
         SwingMarkPanel.this.setSelectedIndex(selection);
         SwingMarkPanel.this.repaint();
      }
   }
}
