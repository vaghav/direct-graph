package com.collibra.message.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.regex.Matcher;

import static com.collibra.message.util.CommandExtractor.clientNamePattern;

/**
 * Utility class for message exchange.
 */
public final class CommunicationUtil {
    private CommunicationUtil() {
    }

    public static void sendMessage(PrintWriter outData, String message) {
        System.out.println("Sending message " + message);
        outData.println(message);
    }

    public static String readReceivedMessage(BufferedReader inData) throws IOException {
        String incomingData = inData.readLine();
        if (incomingData == null) {
            // Null means that a client closed a socket
            throw new IOException("Socket closed");
        }
        return incomingData;
    }

    public static String handshake(PrintWriter outData, BufferedReader inData) throws IOException {
        sendMessage(outData, "HI, I AM " + UUID.randomUUID());
        String receivedMessage = readReceivedMessage(inData);
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        if (!clientNameMatcher.find()) {
            throw new IllegalArgumentException(String.format("[%s] is not valid command for greeting",
                    receivedMessage));
        }
        sendMessage(outData, "HI " + clientNameMatcher.group("clientName"));
        return extractClientName(receivedMessage);
    }

    public static String extractClientName(String receivedMessage) {
        Matcher clientNameMatcher = clientNamePattern.matcher(receivedMessage);
        if (clientNameMatcher.find()) {
            return clientNameMatcher.group("clientName");
        }
        throw new IllegalArgumentException(String.format("Couldn't not extract 'clientname' " +
                "from received [%s] message. ", receivedMessage));
    }
}