package com.collibra.command.handlers;

import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

import static com.collibra.message.util.MessageUtil.sendMessage;

/**
 * Invalid command handler.
 */
public class InvalidCommandHandler implements CommandHandler {
    @Override
    public void handleCommand(PrintWriter outData, ParsedMessage parsedMessage) {
        sendMessage(outData, "SORRY, I DID NOT UNDERSTAND THAT");
    }
}
