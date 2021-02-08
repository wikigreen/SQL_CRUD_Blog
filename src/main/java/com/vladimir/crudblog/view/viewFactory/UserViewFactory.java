package com.vladimir.crudblog.view.viewFactory;

import com.vladimir.crudblog.view.UserView;
import com.vladimir.crudblog.view.View;

public class UserViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new UserView();
    }
}
