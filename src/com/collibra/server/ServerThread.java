package com.collibra.server;

import com.collibra.command.handlers.ByeCommandHandler;
import com.collibra.command.handlers.CommandHandler;
import com.collibra.command.handlers.GreetingCommandHandler;
import com.collibra.message.util.Command;
import com.collibra.message.util.ParsedMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

import static com.collibra.message.util.Command.BYE;
import static com.collibra.message.util.Command.HI;
import static com.collibra.message.util.MessageParser.parseReceivedMessage;
import static com.collibra.message.util.MessageUtil.handshake;
import static com.collibra.message.util.MessageUtil.readReceivedMessage;

/**
 * Thread which is responsible to handle client connection and graph processing.
 */
public class ServerThread extends Thread {

    private final Socket socket;
    private final Map<Command, CommandHandler> commandToHandler;

    public ServerThread(Socket socket, Map<Command, CommandHandler> commandToHandler) {
        this.socket = socket;
        this.commandToHandler = commandToHandler;
    }

    public void run() {
        long startTime = System.nanoTime();
        PrintWriter outData = null;
        String clientName;
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outData = new PrintWriter(socket.getOutputStream(), true);
            handshake(outData);
            while (true) {
                ParsedMessage parsedMessage = parseReceivedMessage(readReceivedMessage(inData));
                clientName = parsedMessage.getClientName();
                commandToHandler.put(HI, new GreetingCommandHandler(clientName));
                if (clientName != null) {
                    commandToHandler.put(BYE, new ByeCommandHandler(clientName, startTime));
                }
                commandToHandler.get(parsedMessage.getCommand()).handleCommand(outData, parsedMessage);
            }
        } catch (SocketTimeoutException ex) {
            handleSocketTimeoutException(outData, ex);
        } catch (IOException ex) {
            System.out.printf("Connection interrupted due to [%s]%n", ex.getCause());
        } finally {
            releaseResources(outData);
        }
    }

    private void handleSocketTimeoutException(PrintWriter outData, SocketTimeoutException ex) {
        if (outData != null) {
            commandToHandler.get(BYE).handleCommand(outData,
                    new ParsedMessage.CommandMessageBuilder().build());
        } else {
            System.out.printf("Socket timeout exception happened: [%s]", ex.getCause());
        }
    }

    private void releaseResources(PrintWriter outData) {
        if (outData != null) {
            outData.close();
        }
        try {
            socket.close();
        } catch (IOException ex) {
            System.out.println("Unable close he socket: " + ex.getMessage());
        }
    }
}
