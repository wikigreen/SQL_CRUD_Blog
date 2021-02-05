package com.vladimir.crudblog.view.commands;

import com.vladimir.crudblog.view.View;
import com.vladimir.crudblog.view.viewFactory.ViewFactoryCreator;

public class ReadAllCommand implements Command{
    private final View view;

    public ReadAllCommand(String type) throws UnknownCommandException {
        this.view = new ViewFactoryCreator().createViewFactoryByName(type).createView();
    }

    @Override
    public void execute() {
        view.readAll();
    }
}
