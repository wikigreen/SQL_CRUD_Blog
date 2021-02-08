package com.vladimir.crudblog.view.commands.commandFactory;

import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.view.commands.UnknownCommandException;

public class CommandFactoryCreator {
    private SQLConnectionImpl service;

    public CommandFactoryCreator(SQLConnectionImpl service) {
        this.service = service;
    }

    public CommandFactory createCommandFactory(String command) throws UnknownCommandException {
        String[] commands = command.split(" +");
        String firstCommand = commands[0];

        String type = null;
        String id = null;
        try{
            type = commands[1];
            id = commands[2];
        } catch (ArrayIndexOutOfBoundsException ignored){}


        return switch (firstCommand){
            case "exit": yield new ExitCommandFactory();
            case "help": yield new HelpCommandFactory();

            case "create": yield new CreateCommandFactory(type);
            case "read_all": yield new ReadAllCommandFactory(type);

            case "read": yield new ReadCommandFactory(type, id);
            case "update": yield new UpdateCommandFactory(type, id);
            case "delete": yield new DeleteCommandFactory(type, id);

            default: throw new UnknownCommandException("'" + command + "'" + " is not a command" +
                    "\n" + "Type 'help' to see all commands.");
        };
    }
}
