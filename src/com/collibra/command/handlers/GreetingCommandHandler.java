package com.collibra.command.handlers;

import java.io.PrintWriter;
import java.util.regex.Matcher;

import static com.collibra.message.util.MessageParser.clientNamePattern;
import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Greeting edge command handler implementation.
 */
public class GreetingCommandHandler implements CommandHandler {

    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        if (clientNameMatcher.find()) {
            sendMessage(outData, "HI " + clientNameMatcher.group("clientName"));
        }
    }
}
