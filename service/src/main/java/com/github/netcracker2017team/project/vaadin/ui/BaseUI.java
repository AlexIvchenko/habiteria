package com.github.netcracker2017team.project.vaadin.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alex Ivchenko
 */
public abstract class BaseUI extends UI {
    private SpringViewProvider viewProvider;

    @Autowired
    public void setViewProvider(SpringViewProvider viewProvider) {
        this.viewProvider = viewProvider;
    }

    protected abstract MenuBar initMenu();
    protected abstract void defaultNavigate();

    private Component create() {
        VerticalLayout layout = new VerticalLayout();
        Panel viewContent = new Panel();
        viewContent.setSizeFull();
        initNavigator(viewContent);
        MenuBar menu = initMenu();
        layout.addComponents(menu, viewContent);
        layout.setComponentAlignment(menu, Alignment.TOP_RIGHT);
        return layout;
    }

    private void initNavigator(Panel viewContent) {
        Navigator navigator = new Navigator(this, viewContent);
        navigator.addProvider(viewProvider);
        setNavigator(navigator);
    }

    @Override
    protected final void init(VaadinRequest request) {
        setContent(create());
        defaultNavigate();
    }
}