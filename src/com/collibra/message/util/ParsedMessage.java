package com.collibra.message.util;

/**
 * Represents command with message metadata received from the client.
 */
public class ParsedMessage {
    private final Command command;

    private final String sourceNodeName;

    private final String destinationNodeName;

    private final int weight;

    private final String clientName;

    public ParsedMessage(CommandMessageBuilder messageBuilder) {
        this.command = messageBuilder.command;
        this.sourceNodeName = messageBuilder.sourceNodeName;
        this.destinationNodeName = messageBuilder.destinationNodeName;
        this.weight = messageBuilder.weight;
        this.clientName = messageBuilder.clientName;
    }

    public Command getCommand() {
        return command;
    }

    public String getSourceNodeName() {
        return sourceNodeName;
    }

    public String getDestinationNodeName() {
        return destinationNodeName;
    }

    public int getWeight() {
        return weight;
    }

    public String getClientName() {
        return clientName;
    }

    public static class CommandMessageBuilder {
        private Command command;
        private String sourceNodeName;
        private String destinationNodeName;
        private int weight;
        public String clientName;

        public CommandMessageBuilder setCommand(Command command) {
            this.command = command;
            return this;
        }

        public CommandMessageBuilder setSourceNodeName(String sourceNodeName) {
            this.sourceNodeName = sourceNodeName;
            return this;
        }

        public CommandMessageBuilder setDestinationNodeName(String destinationNodeName) {
            this.destinationNodeName = destinationNodeName;
            return this;
        }

        public CommandMessageBuilder setWeight(int weight) {
            this.weight = weight;
            return this;
        }

        public CommandMessageBuilder setClientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public ParsedMessage build() {
            return new ParsedMessage(this);
        }
    }
}
