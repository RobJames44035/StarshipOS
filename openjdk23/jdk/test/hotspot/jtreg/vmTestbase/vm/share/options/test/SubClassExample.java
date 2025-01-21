/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
/*
 * This is a very simple test for our framework.
 */
package vm.share.options.test;

import vm.share.options.Option;
import vm.share.options.OptionSupport;

/**
 * This is an example with annotated superclasses
 * @see vm.share.options.Option
 * @see vm.share.options.OptionSupport
 */
public class SubClassExample extends SimpleExampleWithOptionsAnnotation
{

    @Option(name = "timeout", default_value = "100", description = "timeout")
    int timeout;


    @Option(name = "logging_mode", description = "quiet or verbose")
    private String logging_mode;

    @Override
    public void run()
    {
    // ..do actual testing here..
         System.out.println("iterations = " + iterations);
         System.out.println("RM : " + running_mode);
         System.out.println("logging_mode" + logging_mode);
         System.out.println("timeout " + timeout);
         System.out.println("stresstime " + stressOptions.getStressTime());
    }


    public static void main(String[] args)
    {
        SubClassExample test = new SubClassExample();
        OptionSupport.setup(test, args); // instead of manually
                                         // parsing arguments
        // now test.iterations is set to 10 or 100 (default value).
        test.run();
    }
}
