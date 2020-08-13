package com.collibra.message.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for message parsing.
 */
public final class MessageParser {
    public static final Pattern clientNamePattern = Pattern.compile("^HI, I AM (?<clientName>[A-Za-z0-9\\-]+)$");

    private MessageParser() {
    }

    public static Command extractCommand(String receivedMessage) {
        if (receivedMessage.startsWith("HI, I AM")) {
            return Command.HI;
        } else if ("BYE MATE!".equals(receivedMessage)) {
            return Command.BYE;
        } else if (receivedMessage.startsWith("ADD NODE")) {
            return Command.ADD_NODE;
        } else if (receivedMessage.startsWith("REMOVE NODE")) {
            return Command.REMOVE_NODE;
        } else if (receivedMessage.startsWith("ADD EDGE")) {
            return Command.ADD_EDGE;
        } else if (receivedMessage.startsWith("REMOVE EDGE")) {
            return Command.REMOVE_EDGE;
        }
        return Command.INVALID;
    }

    public static String extractClientName(String receivedMessage) {
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        if (clientNameMatcher.find()) {
            return clientNameMatcher.group("clientName");
        }
        throw new IllegalStateException("Invalid message received");
    }
}
