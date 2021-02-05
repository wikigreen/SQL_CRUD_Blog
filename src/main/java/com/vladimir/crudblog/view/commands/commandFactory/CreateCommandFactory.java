package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.CreateCommand;
import com.vladimir.crudblog.view.commands.UnknownCommandException;

public class CreateCommandFactory implements CommandFactory {
    private final String type;

    public CreateCommandFactory(String type) {
        this.type = type;
    }

    @Override
    public Command createCommand() throws UnknownCommandException {
        if(type == null)
            throw new UnknownCommandException("After command 'create' should be a type of object ('region', 'post' or 'user') and id, if necessary" + "\n"
                    + "For example: 'create' region");
        return new CreateCommand(type);
    }
}
