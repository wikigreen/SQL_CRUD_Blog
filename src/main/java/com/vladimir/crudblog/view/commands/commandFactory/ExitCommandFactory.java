package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.ExitCommand;

public class ExitCommandFactory implements CommandFactory{
    public ExitCommandFactory() {
    }

    @Override
    public Command createCommand() {
        return new ExitCommand();
    }
}
