package com.collibra.command.handlers;

import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Greeting edge command handler implementation.
 */
public class GreetingCommandHandler implements CommandHandler {
    private final String clientName;

    public GreetingCommandHandler(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        parsedMessage.getClientName();
        sendMessage(outData, "HI " + clientName);
    }
}
