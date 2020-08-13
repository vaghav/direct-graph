package com.collibra.command.handlers;

import com.collibra.message.util.ParsedMessage;

import java.io.PrintWriter;

public interface CommandHandler {
    void handleCommand(PrintWriter outData, ParsedMessage parsedMessage);
}
