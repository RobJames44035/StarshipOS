/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * The test base class for JNDI related tests.
 *
 * run() will be the entry to launch whole tests, base test sequence is
 * initRes(), initTest(), setupTest() and launch().
 *
 * launch() will call real test logic runTest() which required to be implemented
 * in test class.
 *
 * exception handling logic should be placed in handleException().
 *
 * cleanup related should be placed in cleanupTest which been called in finally
 * block.
 */
public abstract class TestBase {

    /**
     * The entry to the test.
     *
     * @param args given input arguments
     * @throws Exception if any exception
     */
    public void run(String[] args) throws Exception {
        initRes();
        initTest(args);
        setupTest();
        launch();
    }

    /**
     * Initial local resources, such as socket.
     *
     * @throws Exception if any exception
     */
    public void initRes() throws Exception {
    }

    /**
     * Initial test with given arguments.
     *
     * @param args given arguments
     */
    public void initTest(String[] args) {
    }

    /**
     * Setup test.
     */
    public void setupTest() {
    }

    /**
     * Launch test.
     *
     * @throws Exception if any exception
     */
    public void launch() throws Exception {
        try {
            runTest();
        } catch (Exception e) {
            if (!handleException(e)) {
                throw e;
            }
        } finally {
            cleanupTest();
        }
    }

    /**
     * The real test logic to run, this required to be implemented in test class.
     *
     * @throws Exception if any exception
     */
    public abstract void runTest() throws Exception;

    /**
     * Handle test exception.
     *
     * @param e exception which been thrown during test runTest()
     * @return <tt>true</tt> if given exception is expected
     */
    public boolean handleException(Exception e) {
        return false;
    }

    /**
     * Cleanup test.
     */
    public abstract void cleanupTest();
}
