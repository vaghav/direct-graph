package com.collibra.command.handlers;

import java.io.PrintWriter;

import static com.collibra.message.util.CommunicationUtil.sendMessage;

/**
 * Bye edge command handler implementation.
 */
public class ByeCommandHandler implements CommandHandler {
    private final String clientName;
    private final long startTime;

    public ByeCommandHandler(String clientName, long startTime) {
        this.clientName = clientName;
        this.startTime = startTime;
    }

    @Override
    public void handleCommand(PrintWriter outData, String parsedMessage) {
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        sendMessage(outData, String.format("BYE %s, WE SPOKE FOR %d MS", clientName, duration));
    }
}
