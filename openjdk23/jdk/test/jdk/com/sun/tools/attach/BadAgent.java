/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 *
 *
 * A "bad" agent. Used by the unit tests for the attach mechanism to test
 * the behaviour when agentmain throws an exception.
 */
public class BadAgent {

    public static void agentmain(String args) {
        throw new RuntimeException("Something bad happened - Bye!");
    }

}
