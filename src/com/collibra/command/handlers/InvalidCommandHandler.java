package com.collibra.command.handlers;

import java.io.PrintWriter;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Invalid command handler.
 */
public class InvalidCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage) {
        sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
    }
}
