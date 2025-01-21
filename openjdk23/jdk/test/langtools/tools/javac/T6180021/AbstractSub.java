/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6180021
 * @summary CompletionFailure during TypeTrans
 * @author  Peter von der Ah\u00e9
 * @compile Super.java
 * @clean   Missing
 * @compile AbstractSub.java
 */
abstract class AbstractSub extends AbstractSuper {}
