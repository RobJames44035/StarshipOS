/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package test.auctionportal;

import org.w3c.dom.ls.LSOutput;
import java.io.OutputStream;
import java.io.Writer;

/**
 * A Thread-safe LS output destination for DOM processing. LSOutput objects
 * belong to the application. The DOM implementation will never modify them
 * (though it may make copies and modify the copies, if necessary).
 */
public class MyDOMOutput implements LSOutput {
    /**
     * An attribute of a language and binding dependent type that represents a
     * writable stream of bytes.
     */
    private OutputStream bytestream;

    /**
     * character encoding to use for the output.
     */
    private String encoding;

    /**
     * The system identifier.
     */
    private String sysId;

    /**
     * Writable stream to which 16-bit units can be output.
     */
    private Writer writer;

    /**
     * An attribute of a language and binding dependent type that represents a
     * writable stream of bytes.
     *
     * @return a writable stream.
     */
    @Override
    public OutputStream getByteStream() {
        return bytestream;
    }

    /**
     * An attribute of a language and binding dependent type that represents a
     * writable stream to which 16-bit units can be output.
     *
     * @return writable stream instance.
     */
    @Override
    public Writer getCharacterStream() {
        return writer;
    }

    /**
     * The character encoding to use for the output.
     *
     * @return the character encoding.
     */
    @Override
    public String getEncoding() {
        return encoding;
    }

    /**
     * The system identifier for this output destination.
     *
     * @return system identifier.
     */
    @Override
    public String getSystemId() {
        return sysId;
    }

    /**
     * Set writable stream of bytes.
     *
     * @param bs OutputStream instance
     */
    @Override
    public void setByteStream(OutputStream bs) {
        bytestream = bs;
    }

    /**
     * Set 16 bits unit writable stream.
     *
     * @param cs a Writer instance
     */
    @Override
    public void setCharacterStream(Writer cs) {
        writer = cs;
    }

    /**
     * Set character encoding to use for the output.
     *
     * @param encoding encoding set to the output
     */
    @Override
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * Set the system identifier for the output.
     *
     * @param sysId system identifier string.
     */
    @Override
    public void setSystemId(String sysId) {
        this.sysId = sysId;
    }
}
