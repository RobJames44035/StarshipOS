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

    // this version of the class "accidentally" does nothing. Meant to be compared with "corrected" bytecode
    public void
    doSomething()
        {

        }
    }
