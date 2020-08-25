package com.collibra.command.handlers;

import com.collibra.message.util.SessionContext;

import java.io.PrintWriter;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Implements invalid command handler.
 */
public class InvalidCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(PrintWriter outData, String receivedMessage, SessionContext sessionContext) {
        sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
    }
}
