package com.collibra.server;

import com.collibra.command.handlers.CommandHandler;
import com.collibra.message.util.Command;
import com.collibra.message.util.SessionContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Map;

import static com.collibra.message.util.Command.BYE;
import static com.collibra.message.util.Command.INVALID;
import static com.collibra.message.util.CommandExtractor.extractCommand;
import static com.collibra.message.util.CommunicationUtil.handshake;
import static com.collibra.message.util.CommunicationUtil.readReceivedMessage;

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
        try {
            BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outData = new PrintWriter(socket.getOutputStream(), true);
            String clientName = handshake(outData, inData);
            while (true) {
                String receivedMessage = readReceivedMessage(inData);
                commandToHandler.get(extractCommand(receivedMessage)).handleCommand(outData, receivedMessage,
                        new SessionContext(clientName, startTime));
            }
        } catch (SocketTimeoutException ex) {
            handleSocketTimeoutException(outData, ex);
        } catch (IOException ex) {
            System.out.printf("Connection interrupted due to [%s]%n", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.printf("Received invalid arguments for command. [%s]%n", ex.getMessage());
            commandToHandler.get(INVALID).handleCommand(outData, "", new SessionContext());
        } finally {
            releaseResources(outData);
        }
    }

    private void handleSocketTimeoutException(PrintWriter outData, SocketTimeoutException ex) {
        if (outData != null) {
            commandToHandler.get(BYE).handleCommand(outData, null, new SessionContext());
        } else {
            System.out.printf("Socket timeout exception happened: [%s]", ex.getMessage());
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
