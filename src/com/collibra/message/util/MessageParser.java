package com.collibra.message.util;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for message parsing.
 */
public final class MessageParser {

    private static final Pattern clientNamePattern = Pattern.compile("^HI, I AM (?<clientName>[A-Za-z0-9\\-]+)$");
    private static final Pattern addNodePattern = Pattern.compile("^ADD NODE (?<nodeName>[A-Za-z0-9\\-]+)$");
    private static final Pattern removeNodePattern = Pattern.compile("^REMOVE NODE (?<nodeName>[A-Za-z0-9\\-]+)$");
    private static final Pattern addEdgePattern = Pattern.compile("^ADD EDGE (?<fromNodeName>[A-Za-z0-9\\-]+) " +
            "(?<toNodeName>[A-Za-z0-9\\-]+) (?<weight>[0-9]+)$");
    private static final Pattern removeEdgePattern = Pattern.compile("^REMOVE EDGE (?<fromNodeName>[A-Za-z0-9\\-]+) " +
            "(?<toNodeName>[A-Za-z0-9\\-]+)$");

    private MessageParser() {
    }

    public static ParsedMessage parseReceivedMessage(java.lang.String receivedMessage) {
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        Matcher addNodeMatcher = addNodePattern.matcher(receivedMessage);
        Matcher removeNodeMatcher = removeNodePattern.matcher(receivedMessage);
        Matcher addEdgeMatcher = addEdgePattern.matcher(receivedMessage);
        Matcher removeEdgeMatcher = removeEdgePattern.matcher(receivedMessage);

        //TODO: Use strategy or state pattern to get rid of if conditions using Lambdas.
        if (clientNameMatcher.find()) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.HI)
                    .setClientName(clientNameMatcher.group("clientName")).build();
        } else if ("BYE MATE!".equals(receivedMessage)) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.BYE).build();
        } else if (addNodeMatcher.find()) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.ADD_NODE)
                    .setSourceNodeName(addNodeMatcher.group("nodeName")).build();
        } else if (removeNodeMatcher.find()) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.REMOVE_NODE)
                    .setSourceNodeName(removeNodeMatcher.group("nodeName")).build();
        } else if (addEdgeMatcher.find()) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.ADD_EDGE)
                    .setSourceNodeName(addEdgeMatcher.group("fromNodeName")).
                            setDestinationNodeName(addEdgeMatcher.group("toNodeName"))
                    .setWeight(Integer.parseInt(addEdgeMatcher.group("weight"))).build();
        } else if (removeEdgeMatcher.find()) {
            return new ParsedMessage.CommandMessageBuilder().setCommand(Command.REMOVE_EDGE)
                    .setSourceNodeName(removeEdgeMatcher.group("fromNodeName"))
                    .setDestinationNodeName(removeEdgeMatcher.group("toNodeName")).build();
        }
        return new ParsedMessage.CommandMessageBuilder().setCommand(Command.INVALID).build();
    }


    public static void sendMessage(PrintWriter outData, String message) {
        System.out.println("Sending message " + message);
        outData.println(message);
    }
}
