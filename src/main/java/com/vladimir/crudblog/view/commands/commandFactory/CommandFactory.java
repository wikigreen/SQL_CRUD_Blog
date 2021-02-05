package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.UnknownCommandException;

public interface CommandFactory {
    Command createCommand() throws UnknownCommandException;
}
