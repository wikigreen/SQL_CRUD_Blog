package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.ReadCommand;
import com.vladimir.crudblog.view.commands.UnknownCommandException;

public class ReadCommandFactory implements CommandFactory{
    private final String type;
    private final String id;

    public ReadCommandFactory(String type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Command createCommand() throws UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'read_all' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'read_all' region");
        if(id == null)
            throw new UnknownCommandException("After command 'read " + type + "' should be an ID" + "\n"
                    + "For example: 'read " + type + " 1'");
        return new ReadCommand(type, id);
    }
}
