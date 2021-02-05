package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.UnknownCommandException;
import com.vladimir.crudblog.view.commands.UpdateCommand;

public class UpdateCommandFactory implements CommandFactory {
    private final String type;
    private final String id;

    public UpdateCommandFactory(String type, String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public Command createCommand() throws  UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'update' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'update' region");
        if(id == null)
            throw new UnknownCommandException("After command 'update " + type + "' should be an ID" + "\n"
                    + "For example: 'update " + type + " 1'");
        return new UpdateCommand(type, id);
    }
}
