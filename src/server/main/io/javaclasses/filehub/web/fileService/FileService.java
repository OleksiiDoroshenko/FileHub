package io.javaclasses.filehub.web.fileService;

import io.javaclasses.filehub.web.Command;
import io.javaclasses.filehub.web.Service;
import io.javaclasses.filehub.web.State;

public class FileService implements Service {

    private final State state;

    public FileService(State initState) {
        this.state = initState;
    }

    @Override
    public void process(Command command) {
        command.execute(state);
    }
}
