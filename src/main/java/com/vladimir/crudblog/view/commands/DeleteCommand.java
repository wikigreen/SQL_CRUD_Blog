package com.vladimir.crudblog.view.commands;

import com.vladimir.crudblog.view.View;
import com.vladimir.crudblog.view.viewFactory.ViewFactoryCreator;

public class DeleteCommand implements Command {
    private final View view;
    private final Long id;

    public DeleteCommand(String type, String id) throws UnknownCommandException {
        this.view = new ViewFactoryCreator().createViewFactoryByName(type).createView();
        try {
            this.id = Long.parseLong(id);
        } catch (NumberFormatException e){
            throw new UnknownCommandException("'" + id + "' is not a number");
        }
    }

    @Override
    public void execute() {
        view.delete(id);
    }
}
