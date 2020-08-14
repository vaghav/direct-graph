package com.collibra.command.handlers;

import java.io.PrintWriter;
import java.util.regex.Matcher;

import static com.collibra.message.util.CommandExtractor.clientNamePattern;
import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Implements greeting command handler.
 */
public class GreetingCommandHandler implements CommandHandler {

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        if (!clientNameMatcher.find()) {
            throw new IllegalArgumentException(String.format("[%s] is not valid command for greeting",
                    receivedMessage));
        }
        sendMessage(outData, "HI " + clientNameMatcher.group("clientName"));
    }
}
