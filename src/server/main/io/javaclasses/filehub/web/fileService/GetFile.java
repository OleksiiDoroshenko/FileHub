package io.javaclasses.filehub.web.fileService;

import io.javaclasses.filehub.web.Command;
import io.javaclasses.filehub.web.State;

public class GetFile implements Command {

    private final State state;

    public GetFile(State initState) {
        this.state = initState;
    }


    @Override
    public void execute(State state) {

    }
}
