/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

package nsk.share.jdwp;

import nsk.share.*;

/**
 * This class represents a JDWP command packet.
 */
public class CommandPacket extends Packet {

    /**
     * Static counter for enumeration of the command packets.
     */
    private static int nextID = 1;

    /**
     * Return next free number for enumeration of the command packets.
     */
    public static int getLastID() {
        return (nextID - 1);
    }

    /**
     * Make JDWP command packet for specified command.
     */
    public CommandPacket(int fullCommand) {
        super();

        setPacketID(nextID++);
        setFlags(JDWP.Flag.NONE);
        setFullCommand(fullCommand);
    }

    /**
     * Make JDWP command packet for specified command.
     */
    public CommandPacket(int fullCommand, int id) {
        super();

        setPacketID(id);
        setFlags(JDWP.Flag.NONE);
        setFullCommand(fullCommand);
    }

    /**
     * Make command packet with data from the specified byte buffer.
     */
//    public CommandPacket(ByteBuffer packet) {
    public CommandPacket(Packet packet) {
        super(packet);
    }

    /**
     * Return full command number for this packet.
     */
    public int getFullCommand() {
        int id = 0;

        try {
            id = (int) getID(FullCommandOffset, 2);
        }
        catch (BoundException e) {
            throw new Failure("Caught unexpected exception while getting command number from header:\n\t"
                            + e);
        }

        return id;
    }

    /**
     * Return short command number for this packet.
     */
    public byte getCommand() {
        byte id = 0;

        try {
            id = getByte(CommandOffset);
        }
        catch (BoundException e) {
            throw new Failure("Caught unexpected exception while getting command number from header:\n\t"
                            + e);
        }

        return id;
    }

    /**
     * Return command set number for this packet.
     */
    public byte getCommandSet() {
        byte id = 0;

        try {
            id = getByte(CommandSetOffset);
        }
        catch (BoundException e) {
            throw new Failure("Caught unexpected exception while getting command number from header:\n\t"
                            + e);
        }

        return id;
    }

    /**
     * Assign command number for this packet.
     */
    public void setFullCommand(int fullCommand) {
        try {
            putID(FullCommandOffset, fullCommand, 2);
        }
        catch (BoundException e) {
            throw new Failure("Caught unexpected exception while setting command number into header: "
                            + e);
        }
    }

    /**
     * Return string representation of the command packet header.
     */
    public String headerToString() {
        return super.headerToString()
             + "    " + toHexString(CommandSetOffset, 4) + " (cmd set): 0x" + toHexDecString(getCommandSet(), 2) + "\n"
             + "    " + toHexString(CommandOffset,    4) + " (command): 0x" + toHexDecString(getCommand(), 2) + "\n";
    }

}
