package com.vladimir.crudblog.view.viewFactory;

import com.vladimir.crudblog.view.PostView;
import com.vladimir.crudblog.view.View;

public class PostViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new PostView();
    }
}
