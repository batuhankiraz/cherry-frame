package com.cherryframe.cherryframe.controller;

import com.cherryframe.cherryframe.CherryFrameApplication;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;

import static com.cherryframe.cherryframe.controller.constants.CherryFrameControllerConstants.Page.STOCK_MANAGER_PAGE_UID;


public class LoginController {

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

    @FXML
    protected void onLogin() throws IOException {
        final var app = new CherryFrameApplication();
        // TODO: connect DB
        // TODO: fill workspaces
        // TODO: validate fields
        // TODO: validate credentials and login
    }

}