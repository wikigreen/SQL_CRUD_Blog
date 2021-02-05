package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.ReadAllCommand;
import com.vladimir.crudblog.view.commands.UnknownCommandException;

public class ReadAllCommandFactory implements CommandFactory {
    private final String type;

    public ReadAllCommandFactory(String type) {
        this.type = type;
    }

    @Override
    public Command createCommand() throws UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'read_all' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'read_all' region");
        return new ReadAllCommand(type);
    }
}
