package com.cherryframe.cherryframe.storefront.constants;

public final class CPMSControllerConstants {

    public interface Page {
        String MANAGEMENT_STUDIO_PAGE_UID = "frame/management-studio.fxml";
        String LOGIN_PAGE_UID = "frame/login.fxml";
    }

    public interface ValidationMessages {
        String UNABLE_TO_LOGIN_EMPTY_CREDENTIALS = "Unable to login into Cherry Pick Management Studio. Please check your credentials and try again!";
        String UNABLE_TO_LOGIN_INVALID_CREDENTIALS = "Wrong username or password! Please check your credentials and try again!";
        String MANDATORY_STOCK_CODE_CHECK = "Stock Code field need to be checked!";
        String UNABLE_TO_IMPORT_DATA_INTO_DATABASE = "Please check your selected items. There are several items which are not matched with a value!";
        String SUCCESSFULLY_IMPORTED = "Successfully Imported!";
        String UNABLE_TO_RESET_PASSWORD = "Passwords doesn't match with each other. Please correct your passwords and try again!";
        String SUCCESSFULLY_RESET_PASSWORD = "Your password has been changed successfully.";
        String SUCCESSFULLY_CHANGED_WORKSPACE = "Your current workspace has been changed successfully.";
    }

    public interface ValidationStyles {
        String CHOICE_BOX_ON_ERROR_DETECTION_STYLE = "-fx-background-color: #f2be04; -fx-border-radius: 15; -fx-border-style: solid inside; -fx-background-radius: 15; -fx-border-color: red";
        String SUCCESSFULLY_IMPORTED_TEXT_STYLE = "-fx-text-inner-color: green";
        String UNABLE_TO_RESET_PASSWORD_STYLE = "-fx-background-color: #edede8; -fx-border-color: red; -fx-border-radius: 7px;";
    }
}
