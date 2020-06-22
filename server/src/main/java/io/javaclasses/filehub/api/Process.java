package io.javaclasses.filehub.api;

import io.javaclasses.filehub.storage.RecordId;

/**
 * Main interface for all process that can be held in the server.
 *
 * @param <Command> - specific command type.
 * @param <Id>      - specific id Type.
 */
public interface Process<Command, Id> {
    /**
     * Process specific command which type was passed from generic.
     *
     * @param command - command to be processed.
     * @return class that implements {@link RecordId} interface.
     */
    Id handle(Command command);
}
