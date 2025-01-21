/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

/*
 * reserved comment block
 * DO NOT REMOVE OR ALTER!
 */
package sun.security.pkcs11.wrapper;



/**
 * class CK_SLOT_INFO provides information about a slot.<p>
 * <B>PKCS#11 structure:</B>
 * <PRE>
 *  typedef struct CK_SLOT_INFO {&nbsp;&nbsp;
 *    CK_UTF8CHAR slotDescription[64];&nbsp;&nbsp;
 *    CK_UTF8CHAR manufacturerID[32];&nbsp;&nbsp;
 *    CK_FLAGS flags;&nbsp;&nbsp;
 *    CK_VERSION hardwareVersion;&nbsp;&nbsp;
 *    CK_VERSION firmwareVersion;&nbsp;&nbsp;
 *  } CK_SLOT_INFO;
 * </PRE>
 *
 * @author Karl Scheibelhofer <Karl.Scheibelhofer@iaik.at>
 * @author Martin Schlaeffer <schlaeff@sbox.tugraz.at>
 */
public class CK_SLOT_INFO {

    /* slotDescription and manufacturerID have been changed from
     * CK_CHAR to CK_UTF8CHAR for v2.11. */

    /**
     * must be blank padded and only the first 64 chars will be used<p>
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_UTF8CHAR slotDescription[64];
     * </PRE>
     */
    public char[] slotDescription;

    /**
     * must be blank padded and only the first 32 chars will be used<p>
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_UTF8CHAR manufacturerID[32];
     * </PRE>
     */
    public char[] manufacturerID;

    /**
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_FLAGS flags;
     * </PRE>
     */
    public long flags;

    /* hardwareVersion and firmwareVersion are new for v2.0 */
    /**
     * version of hardware<p>
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_VERSION hardwareVersion;
     * </PRE>
     */
    public CK_VERSION hardwareVersion;

    /**
     * version of firmware<p>
     * <B>PKCS#11:</B>
     * <PRE>
     *   CK_VERSION firmwareVersion;
     * </PRE>
     */
    public CK_VERSION firmwareVersion;

    public CK_SLOT_INFO(char[] slotDesc, char[] vendor,
                        long flags, CK_VERSION hwVer, CK_VERSION fwVer) {
        this.slotDescription = slotDesc;
        this.manufacturerID = vendor;
        this.flags = flags;
        this.hardwareVersion = hwVer;
        this.firmwareVersion = fwVer;
    }

    /**
     * Returns the string representation of CK_SLOT_INFO.
     *
     * @return the string representation of CK_SLOT_INFO
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(Constants.INDENT);
        sb.append("slotDescription: ");
        sb.append(new String(slotDescription));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("manufacturerID: ");
        sb.append(new String(manufacturerID));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("flags: ");
        sb.append(Functions.slotInfoFlagsToString(flags));
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("hardwareVersion: ");
        sb.append(hardwareVersion.toString());
        sb.append(Constants.NEWLINE);

        sb.append(Constants.INDENT);
        sb.append("firmwareVersion: ");
        sb.append(firmwareVersion.toString());
        //buffer.append(Constants.NEWLINE);

        return sb.toString() ;
    }

}
