/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.share.options.test;

import vm.share.options.Option;
import vm.share.options.OptionSupport;
import vm.share.options.Options;


/**
 * This is again a simple example which shows the power of @Options annotation.
 * @see vm.share.options.Options
 * @see vm.share.options.Option
 * @see vm.share.options.OptionSupport
 */

public class SimpleExampleWithOptionsAnnotation
{
    @Options
    StressOptions stressOptions = new StressOptions();
    @Option(name = "iterations", default_value = "100", description = "Number of iterations")
    int iterations;

    @Option(description = "quiet or verbose")
    String running_mode;

    public void run()
    {
    // ..do actual testing here..
         System.out.println("iterations = " + iterations);
         System.out.println("RM: " + running_mode);
         System.out.println("StressOptions " + stressOptions.getStressTime());
    }


    public static void main(String[] args)
    {
        SimpleExampleWithOptionsAnnotation test = new SimpleExampleWithOptionsAnnotation();
        OptionSupport.setup(test, args); // instead of manually                       // parsing arguments
        // now test.iterations is set to 10 or 100.
        test.run();
    }
}

class StressOptions
{
    @Option(default_value="30", description="Stress time")
    private long stressTime;

    public long getStressTime()
    {
        return stressTime;
    }

}
