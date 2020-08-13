package com.collibra.command.handlers;

import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

/**
 * Interface for handling commands received from the client.
 */
public interface CommandHandler {
    /**
     * Handle command received from the client.
     * @param outData the socket output data stream.
     * @param parsedMessage the parsed message.
     */
    void handleCommand(PrintWriter outData, ParsedMessage parsedMessage);
}
