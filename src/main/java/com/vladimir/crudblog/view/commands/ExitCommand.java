package com.vladimir.crudblog.view.commands;

import com.vladimir.crudblog.view.MainMenu;

public class ExitCommand implements Command{
    @Override
    public void execute() {
        MainMenu.getInstance().turnOff();
    }
}
