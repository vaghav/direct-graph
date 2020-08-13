package com.collibra.command.handlers;

import java.io.PrintWriter;

/**
 * Interface for handling commands received from the client.
 */
public interface CommandHandler {
    /**
     * Handle command received from the client.
     * @param outData the socket output data stream.
     * @param receivedMessage the received message.
     */
    void handleCommand(PrintWriter outData, String receivedMessage);
}
