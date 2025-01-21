/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package javax.management.modelmbean;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;

/**
 * Exception thrown when an invalid target object type is specified.
 *
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>1190536278266811217L</code>.
 *
 * @since 1.5
 */
public class InvalidTargetObjectTypeException  extends Exception
{
    private static final long serialVersionUID = 1190536278266811217L;
    /**
     * @serialField exception Exception Encapsulated {@link Exception}
     */
    private static final ObjectStreamField[] serialPersistentFields =
    {
      new ObjectStreamField("exception", Exception.class)
    };

    /**
     * @serial Encapsulated {@link Exception}
     */
    Exception exception;


    /**
     * Default constructor.
     */
    public InvalidTargetObjectTypeException ()
    {
      super("InvalidTargetObjectTypeException: ");
      exception = null;
    }


    /**
     * Constructor from a string.
     *
     * @param s String value that will be incorporated in the message for
     *    this exception.
     */

    public InvalidTargetObjectTypeException (String s)
    {
      super("InvalidTargetObjectTypeException: " + s);
      exception = null;
    }


    /**
     * Constructor taking an exception and a string.
     *
     * @param e Exception that we may have caught to reissue as an
     *    InvalidTargetObjectTypeException.  The message will be used, and we may want to
     *    consider overriding the printStackTrace() methods to get data
     *    pointing back to original throw stack.
     * @param s String value that will be incorporated in message for
     *    this exception.
     */

    public InvalidTargetObjectTypeException (Exception e, String s)
    {
      super("InvalidTargetObjectTypeException: " +
            s +
            ((e != null)?("\n\t triggered by:" + e.toString()):""));
      exception = e;
    }

    /**
     * Deserializes an {@link InvalidTargetObjectTypeException} from an {@link ObjectInputStream}.
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException {
      in.defaultReadObject();
    }


    /**
     * Serializes an {@link InvalidTargetObjectTypeException} to an {@link ObjectOutputStream}.
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {
      out.defaultWriteObject();
    }
}
