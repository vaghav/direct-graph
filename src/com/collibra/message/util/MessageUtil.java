package com.collibra.message.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

/**
 * Utility class for message transfer.
 */
public final class MessageUtil {

    private MessageUtil() {
    }

    public static void sendMessage(PrintWriter outData, String message) {
        System.out.println("Sending message " + message);
        outData.println(message);
    }

    public static String readReceivedMessage(BufferedReader inData) throws IOException {
        String incomingData = inData.readLine();
        if (incomingData == null) {
            // null means that a client closed a socket
            throw new IOException("Socket closed");
        }
        return incomingData;
    }

    public static void handshake(PrintWriter outData) {
        sendMessage(outData, "HI, I AM " + UUID.randomUUID());
    }
}
