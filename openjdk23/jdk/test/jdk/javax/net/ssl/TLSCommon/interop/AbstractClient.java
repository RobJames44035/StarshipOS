/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.io.IOException;

/*
 * An abstract client.
 */
public abstract class AbstractClient extends AbstractPeer implements Client {

    @Override
    protected void printLog() throws IOException {
        System.out.println("---------- Client log start ----------");
        super.printLog();
        System.out.println("---------- Client log end ----------");
    }

    public static abstract class Builder extends AbstractPeer.Builder {

        // Indicate if try to read response.
        private boolean readResponse = true;

        public boolean isReadResponse() {
            return readResponse;
        }

        public Builder setReadResponse(boolean readResponse) {
            this.readResponse = readResponse;
            return this;
        }

        public abstract AbstractClient build() throws Exception;
    }
}
