package com.vladimir.crudblog.view.viewFactory;

import com.vladimir.crudblog.view.commands.UnknownCommandException;

public class ViewFactoryCreator {
    public ViewFactory createViewFactoryByName(String name) throws UnknownCommandException {
        return switch (name){
            case "region": yield new RegionViewFactory();
            case "post": yield new PostViewFactory();
            case "user": yield new UserViewFactory();
            default:
                throw new UnknownCommandException("Type " + name + " is unknown type!");
        };
    }
}
