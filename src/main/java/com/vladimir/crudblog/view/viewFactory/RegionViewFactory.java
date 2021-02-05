package com.vladimir.crudblog.view.viewFactory;

import com.vladimir.crudblog.service.SQLService;
import com.vladimir.crudblog.view.RegionView;
import com.vladimir.crudblog.view.View;

public class RegionViewFactory implements ViewFactory {
    @Override
    public View createView() {
        return new RegionView();
    }
}
