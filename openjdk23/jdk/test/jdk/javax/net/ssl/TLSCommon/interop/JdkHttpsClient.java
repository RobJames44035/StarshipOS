/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.IOException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

/*
 * A JDK client based on HttpsURLConnection.
 */
public class JdkHttpsClient extends AbstractClient {

    protected final int timeout;
    protected final String path;

    protected final SSLContext context;

    public JdkHttpsClient(Builder builder) throws Exception {
        timeout = builder.getTimeout() * 1000;
        path = builder.getPath();

        context = getContext(builder);
    }

    protected SSLContext getContext(Builder builder) throws Exception {
        return builder.getContext() == null
                ? Utilities.createSSLContext(builder.getCertTuple())
                : builder.getContext();
    }

    public static class Builder extends AbstractClient.Builder {

        private String path = "";

        private SSLContext context;

        public String getPath() {
            return path;
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public SSLContext getContext() {
            return context;
        }

        public Builder setContext(SSLContext context) {
            this.context = context;
            return this;
        }

        @Override
        public JdkHttpsClient build() throws Exception {
            return new JdkHttpsClient(this);
        }
    }

    @Override
    public Product getProduct() {
        return Jdk.DEFAULT;
    }

    @Override
    public void connect(String host, int port) throws IOException {
        String urlStr = String.format("https://%s:%d/%s", host, port, path);
        if (Utilities.DEBUG) {
            System.out.println("URL: " + urlStr);
        }

        HttpsURLConnection conn
                = (HttpsURLConnection) new URL(urlStr).openConnection();
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);
        conn.setSSLSocketFactory(context.getSocketFactory());
        conn.connect();
        System.out.println(Utilities.readIn(conn.getInputStream()));
    }
}
