/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vm.share.options.test;

import java.util.Collection;
import vm.share.options.Option;
import vm.share.options.OptionSupport;

/**
 * This example uses @Option annotation with a factory attribute,
 * to populate a field of a non-simple type.
 */
public class ExampleWithNonprimitiveOptions
{

    @Option(name = "iterations", default_value = "100", description = "Number of iterations")
    int iterations;


    @Option(description = "quiet or verbose")
    private String running_mode;

    @Option(description = "type of the list",
            factory=vm.share.options.test.BasicObjectFactoryUsageExample.class)
    private Collection list;

    public void run()
    {
    // ..do actual testing here..
         System.out.println("iterations = " + iterations);
         System.out.println("RM : " + running_mode);
         System.out.println("list is " + list.getClass());
    }


    public static void main(String[] args)
    {
        ExampleWithNonprimitiveOptions test = new ExampleWithNonprimitiveOptions();
        OptionSupport.setup(test, args); // instead of manually                       // parsing arguments
        // now test.iterations is set to 10 or 100.
        test.run();
    }
}
