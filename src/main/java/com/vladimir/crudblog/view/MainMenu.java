package com.vladimir.crudblog.view;

import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.view.commands.Command;
import com.vladimir.crudblog.view.commands.UnknownCommandException;
import com.vladimir.crudblog.view.commands.commandFactory.CommandFactoryCreator;

import java.sql.SQLException;
import java.util.Scanner;

public class MainMenu {
    private boolean isRunning = true;
    private static final MainMenu MAIN_MENU = new MainMenu();
    private final Scanner SCANNER = new Scanner(System.in);

    private MainMenu(){}

    public static MainMenu getInstance(){
        return MAIN_MENU;
    }

    public void run(){
        SQLConnectionImpl service;
        while (true){
            try {
                service = SQLConnectionImpl.getInstance();
                break;
            } catch (SQLException e) {
                System.out.println("Can not connect to database...");
                e.printStackTrace();
            }
        }

        System.out.println("Type 'help' to see all commands.");
        while(isRunning){
            System.out.print("Type command:");
            executeCommand(service);
            System.out.println();
        }
        System.out.println("See you!!!");
    }

    private void executeCommand(SQLConnectionImpl service) {
        String stringCommand = SCANNER.nextLine().trim();
        if("".equals(stringCommand))
            return;
        try {
            Command command = new CommandFactoryCreator(service)
                    .createCommandFactory(stringCommand)
                    .createCommand();
            command.execute();
        } catch (UnknownCommandException e) {
            System.out.println(e.getMessage());
        }
    }      

    public void turnOff() {
        isRunning = false;
    }
}
