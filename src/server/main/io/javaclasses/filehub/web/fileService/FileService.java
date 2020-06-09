package io.javaclasses.filehub.web.fileService;

import io.javaclasses.filehub.web.Command;
import io.javaclasses.filehub.web.Service;

public class FileService implements Service {

    private final FileServiceState state;

    public FileService(FileServiceState initState) {
        this.state = initState;
    }

    @Override
    public void process(Command command) {
        command.execute(state);
    }
}
