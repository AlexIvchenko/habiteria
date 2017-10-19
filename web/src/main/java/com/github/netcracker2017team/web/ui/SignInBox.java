package com.github.netcracker2017team.web.ui;

import com.github.netcracker2017team.web.ui.beans.SignUpBean;
import com.github.netcracker2017team.web.service.UserService;
import com.vaadin.data.BeanValidationBinder;
import com.vaadin.data.Binder;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

import static com.github.netcracker2017team.web.ui.Messages.message;

/**
 * @author Alex Ivchenko
 */
@SpringComponent
@UIScope
public final class SignInBox extends VerticalLayout {
    private UserService userService;

    private TextField username;
    private PasswordField password;
    private Button signIn;
    private Binder<SignUpBean> binder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    private void initialize() {
        config();
        initializeFields();
        initializeSignInButton();
        applyAllComponents();
        username.focus();
    }

    private void config() {
        setWidth("400px");
        setSpacing(true);
        setMargin(true);
    }

    private void initializeFields() {
        username = new TextField(message("label.user.username"));
        password = new PasswordField(message("label.user.password"));
        binder = new BeanValidationBinder<>(SignUpBean.class);
        binder.setBean(new SignUpBean());
        binder.bind(username, "username");
        binder.bind(password, "password");
        binder.addStatusChangeListener(event -> signIn.setEnabled(binder.isValid()));
    }

    private void initializeSignInButton() {
        signIn = new Button(message("label.signIn"));
        signIn.setEnabled(false);
        signIn.addClickListener(
                event -> signIn(binder.getBean()));
    }

    private void signIn(SignUpBean bean) {
        doSignIn(bean.getUsername(), bean.getPassword());
    }

    private void doSignIn(String username, String password) {
        userService.signIn(username, password);
        getUI().getPage().setLocation("/main");
    }

    private void applyAllComponents() {
        addComponents(username, password, signIn);
        forEach(component -> component.setWidth("100%"));
    }
}