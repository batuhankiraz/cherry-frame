package com.cherryframe.cherryframe.storefront.controller;

import com.cherryframe.cherryframe.core.service.user.CPMSUserService;
import com.cherryframe.cherryframe.core.service.user.impl.CPMSUserServiceImpl;
import com.cherryframe.cherryframe.storefront.form.LoginForm;
import com.cherryframe.cherryframe.storefront.validator.CPMSValidator;
import com.cherryframe.cherryframe.storefront.validator.impl.CPMSLoginFormValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.cherryframe.cherryframe.CherryFrameApplication.changeScene;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.Page.MANAGEMENT_STUDIO_PAGE_UID;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationMessages.UNABLE_TO_LOGIN_EMPTY_CREDENTIALS;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationMessages.UNABLE_TO_LOGIN_INVALID_CREDENTIALS;


public class CPMSLoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private ChoiceBox<String> workspaceChoice;
    @FXML
    private ImageView loginFormWarningIcon;
    @FXML
    private Label loginErrorText;

    private final CPMSValidator<LoginForm> loginFormValidator = new CPMSLoginFormValidator();
    private final CPMSUserService cpmsUserService = new CPMSUserServiceImpl();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initWorkspaceChoices();
    }

    @FXML
    protected void onLogin() throws IOException {
        final var loginForm = populateLoginForm(username.getText(), password.getText(), workspaceChoice.getValue());
        final var hasError = !loginFormValidator.validate(loginForm);
        if (hasError) {
            loginFormWarningIcon.setVisible(true);
            loginErrorText.setText(UNABLE_TO_LOGIN_EMPTY_CREDENTIALS);
        }
        else {
            final var success = cpmsUserService.login(loginForm);
            if (success)
            {
                changeScene(MANAGEMENT_STUDIO_PAGE_UID);
            }
            else {
                loginFormWarningIcon.setVisible(true);
                loginErrorText.setText(UNABLE_TO_LOGIN_INVALID_CREDENTIALS);
            }
        }
    }

    private void initWorkspaceChoices() {
        final List<String> items = new ArrayList<>();
        items.add("Table-1");
        items.add("Table-2");
        items.add("Table-3");
        items.add("Table-4");
        items.add("Table-5");
        workspaceChoice.getItems().clear();
        workspaceChoice.setItems(FXCollections.observableList(items));
    }

    private LoginForm populateLoginForm(final String username, final String password, final String workspace) {
        final var form = new LoginForm();
        form.setUsername(username);
        form.setPassword(password);
        form.setWorkspace(workspace);
        return form;
    }
}