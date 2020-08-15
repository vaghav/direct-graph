package com.collibra.message.util;

import java.util.regex.Pattern;

import static com.collibra.message.util.Command.*;

/**
 * Utility class for message parsing.
 */
public final class CommandExtractor {
    public static final Pattern clientNamePattern = Pattern.compile("^HI, I AM (?<clientName>[A-Za-z0-9\\-]+)$");

    private CommandExtractor() {
    }

    public static Command extractCommand(String receivedMessage) {
        if (receivedMessage.startsWith("HI, I AM")) {
            return HI;
        } else if ("BYE MATE!".equals(receivedMessage)) {
            return BYE;
        } else if (receivedMessage.startsWith("ADD NODE")) {
            return ADD_NODE;
        } else if (receivedMessage.startsWith("REMOVE NODE")) {
            return REMOVE_NODE;
        } else if (receivedMessage.startsWith("ADD EDGE")) {
            return ADD_EDGE;
        } else if (receivedMessage.startsWith("REMOVE EDGE")) {
            return REMOVE_EDGE;
        } else if (receivedMessage.startsWith("SHORTEST PATH")) {
            return FIND_SHORTEST_PATH;
        }
        return INVALID;
    }
}
