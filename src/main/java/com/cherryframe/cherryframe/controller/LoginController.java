package com.cherryframe.cherryframe.controller;

import com.cherryframe.cherryframe.CherryFrameApplication;
import javafx.fxml.FXML;
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
    private ImageView loginFormWarningIcon;
    @FXML
    private Label loginErrorText;

    @FXML
    protected void onLogin() throws IOException {
        final var app = new CherryFrameApplication();
        // TODO: connect DB and validate creds
        // TODO: in case of error --> set warning icon as visible and put an informative text
        System.out.println("Login button clicked.");
        if (username.getText().equals("wrong") && password.getText().equals("wrong")) {
            loginFormWarningIcon.setVisible(true);
            loginErrorText.setText("Wrong credentials! Please check your credentials and try again.");
        } else {
            // TODO: redirect customer to stock-manager.fxml
            app.changeScene(STOCK_MANAGER_PAGE_UID);
        }
    }

}