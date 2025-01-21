/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class ExampleRedefine
    {
    private Counter fCounter;

    public
    ExampleRedefine()
        {
        super();
        System.out.println("Simple ExampleRedefine constructor");
        fCounter = new Counter();
        }

    public int
    get()
        {
        return fCounter.get();
        }

    // this redefined version of the class correctly increments the counter.
    // Meant to be compared with "uncorrected" bytecode
    public void
    doSomething()
        {
        fCounter.increment();
        }
    }
