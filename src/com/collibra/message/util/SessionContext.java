package com.collibra.message.util;

public final class SessionContext {
    private final String clientName;
    private final long startTime;

    public SessionContext() {
        this.clientName = "";
        this.startTime = 0;
    }

    public SessionContext(String clientName, long startTime) {
        this.clientName = clientName;
        this.startTime = startTime;
    }

    public String getClientName() {
        return clientName;
    }

    public long getStartTime() {
        return startTime;
    }
}
