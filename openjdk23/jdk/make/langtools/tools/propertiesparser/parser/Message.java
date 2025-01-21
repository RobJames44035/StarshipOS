/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package propertiesparser.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * A message within the message file.
 * A message is a series of lines containing a "name=value" property,
 * optionally preceded by a comment describing the use of placeholders
 * such as {0}, {1}, etc within the property value.
 */
public final class Message {
    final MessageLine firstLine;
    private MessageInfo messageInfo;

    Message(MessageLine l) {
        firstLine = l;
    }

    /**
     * Get the Info object for this message. It may be empty if there
     * if no comment preceding the property specification.
     */
    public MessageInfo getMessageInfo() {
        if (messageInfo == null) {
            MessageLine l = firstLine.prev;
            if (l != null && l.isLint()) {
                l = l.prev;
            }
            if (l != null && l.isInfo())
                messageInfo = new MessageInfo(l.text);
            else
                messageInfo = MessageInfo.dummyInfo;
        }
        return messageInfo;
    }

    /**
     * Get all the lines pertaining to this message.
     */
    public List<MessageLine> getLines(boolean includeAllPrecedingComments) {
        List<MessageLine> lines = new ArrayList<>();
        MessageLine l = firstLine;
        if (includeAllPrecedingComments) {
            // scan back to find end of prev message
            while (l.prev != null && l.prev.isEmptyOrComment())
                l = l.prev;
            // skip leading blank lines
            while (l.text.isEmpty())
                l = l.next;
        } else {
            if (l.prev != null && (l.prev.isInfo() || l.prev.isLint()))
                l = l.prev;
        }

        // include any preceding lines
        for ( ; l != firstLine; l = l.next)
            lines.add(l);

        // include message lines
        for (l = firstLine; l != null && l.hasContinuation(); l = l.next)
            lines.add(l);
        lines.add(l);

        // include trailing blank line if present
        l = l.next;
        if (l != null && l.text.isEmpty())
            lines.add(l);

        return lines;
    }
}
