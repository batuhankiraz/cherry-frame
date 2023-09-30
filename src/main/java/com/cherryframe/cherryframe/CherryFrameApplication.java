package com.cherryframe.cherryframe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static com.cherryframe.cherryframe.controller.constants.CherryFrameControllerConstants.Page.LOGIN_PAGE_UID;

public class CherryFrameApplication extends Application {

    private static final double MAIN_VERTICAL_SIZE = 1400d;
    private static final double MAIN_HORIZONTAL_SIZE = 920d;
    public static Stage STAGE;

    public static void main(final String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        STAGE = primaryStage;
        primaryStage.setResizable(false);
        final Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(LOGIN_PAGE_UID)));
        final Scene scene = new Scene(parent, MAIN_VERTICAL_SIZE, MAIN_HORIZONTAL_SIZE);
        primaryStage.setTitle("Cherry Frame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void changeScene(final String fxmlName) throws IOException {
        final Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlName)));
        STAGE.getScene().setRoot(pane);
    }
}