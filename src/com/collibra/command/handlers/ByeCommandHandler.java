package com.collibra.command.handlers;

import com.collibra.message.util.SessionContext;

import java.io.PrintWriter;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Implements bye command handler.
 */
public class ByeCommandHandler implements CommandHandler {

    @Override
    public void handleCommand(PrintWriter outData, String parsedMessage, SessionContext sessionContext) {
        String clientName = sessionContext.getClientName();
        if (clientName != null) {
            long endTime = System.nanoTime();
            long duration = (endTime - sessionContext.getStartTime()) / 1000000;
            sendMessage(outData, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
        } else {
            sendMessage(outData, "BYE");
        }
    }
}
