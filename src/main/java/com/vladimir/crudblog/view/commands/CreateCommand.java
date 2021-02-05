package com.vladimir.crudblog.view.commands;

import com.vladimir.crudblog.view.View;
import com.vladimir.crudblog.view.viewFactory.ViewFactoryCreator;

public class CreateCommand implements Command {
    private final View view;

    public CreateCommand(String type) throws UnknownCommandException {
        this.view = new ViewFactoryCreator().createViewFactoryByName(type).createView();
    }

    @Override
    public void execute() {
        view.create();
    }
}
