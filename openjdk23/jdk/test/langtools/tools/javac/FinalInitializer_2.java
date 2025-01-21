/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4974917
 * @summary bogus "already initialized" error
 * @author tball
 *
 * @compile FinalInitializer_2.java
 */

public class FinalInitializer_2 {
    // customer-supplied test case
    public void doKMDiscard()
    {

      //the problem will be gone by deleting any of the following
      //5 lines. (just comment them out works too)
      {
        final int t=0;
        final int degCnns[][]=null;
        int sklTmpGrps=0;
      }

      final int sklGrpCnt;
      //the problem will be gone by deleting the loop or
      //the final line or just delete the word 'final'
      for(int i=0;i<1;i++){
        final int j=0;
      }
      sklGrpCnt=0;
    }
}
