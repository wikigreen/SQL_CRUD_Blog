package com.vladimir.crudblog.view.commands;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Types of data objects, that can be handled:\n" +
                "\tregion\t\tRepresents name of region. Used with \"user\" type.\n" +
                "\t\t\t\tContains 'id', that sets automatically and 'name', that can be set by user.\n" +
                "\n" +
                "\tpost\t\tRepresents user`s post.\n" +
                "\t\t\t\tContains 'id', that sets automatically, 'content', that can be set by user,\n" +
                "\t\t\t\t'creation date' and 'modification date'.\n" +
                "\n" +
                "\tuser\t\tRepresents user. \n" +
                "\t\t\t\tContains 'id', that sets automatically, users first and last name, 'role' (user,\n" +
                "\t\t\t\tmoderator or admin), 'region' type (can be get from DB or created during the \n" +
                "\t\t\t\tmaking user), list of 'posts' type, (can be added from DB or created during the \n" +
                "\t\t\t\tmaking user).\n" +
                "\n" +
                "List of commands:\n" +
                "\tcreate <type>\t\tCreates and saves one of supported types to DB.\n" +
                "\t\t\t\t\t\tFor example: \"create region\".\n" +
                "\n" +
                "\tread_all <type>\t\tPrints list of all objects of the specified <type>.\n" +
                "\t\t\t\t\t\tFor example: \"read_all region\".\n" +
                "\n" +
                "\tread <type> <id>\tPrints specific object of type <type> with ID <id>. <id> is number\n" +
                "\t\t\t\t\t\tgreater than 0. For example: \"read region 1\".\n" +
                "\n" +
                "\tupdate <type> <id>\tUpdates specific object of type <type> with ID <id>.\n" +
                "\t\t\t\t\t\tFor example: \"update region 1\".\n" +
                "\n" +
                "\tdelete <type> <id>\tDeletes specific object of type <type> with ID <id> from DB.\n" +
                "\t\t\t\t\t\tFor example: \"delete region 1\".\n");
    }
}
