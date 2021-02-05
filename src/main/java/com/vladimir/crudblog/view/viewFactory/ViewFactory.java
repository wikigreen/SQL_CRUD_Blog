package com.vladimir.crudblog.view.viewFactory;

import com.vladimir.crudblog.service.SQLService;
import com.vladimir.crudblog.view.View;

public interface ViewFactory {
    View createView();
}
