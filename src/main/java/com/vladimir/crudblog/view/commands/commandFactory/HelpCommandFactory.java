package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.HelpCommand;

public class HelpCommandFactory implements CommandFactory {
    @Override
    public Command createCommand() {
        return new HelpCommand();
    }
}
