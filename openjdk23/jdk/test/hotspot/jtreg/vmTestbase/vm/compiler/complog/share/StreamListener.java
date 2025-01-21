/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package vm.compiler.complog.share;

/*
 * StreamListener listens on events from BufferedInputStream.
 *
 * Note: StreamListener should not never block as it potentially
 * runs in thread that reads the input.
 */
public interface StreamListener {
        public void onStart();
        public void onRead(String line);
        public void onFinish();
        public void onException(Throwable e);
}
