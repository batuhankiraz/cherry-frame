package com.cherryframe.cherryframe.storefront.controller;

import com.cherryframe.cherryframe.core.service.file.CPMSFileService;
import com.cherryframe.cherryframe.core.service.file.impl.CPMSFileServiceImpl;
import com.cherryframe.cherryframe.core.service.session.CPMSSessionService;
import com.cherryframe.cherryframe.core.service.session.impl.CPMSSessionServiceImpl;
import com.cherryframe.cherryframe.core.service.user.CPMSUserService;
import com.cherryframe.cherryframe.core.service.user.impl.CPMSUserServiceImpl;
import com.cherryframe.cherryframe.storefront.form.ResetPasswordForm;
import com.cherryframe.cherryframe.storefront.validator.CPMSValidator;
import com.cherryframe.cherryframe.storefront.validator.impl.CPMSResetPasswordValidator;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import static com.cherryframe.cherryframe.CherryFrameApplication.changeScene;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.Page.LOGIN_PAGE_UID;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationMessages.*;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationStyles.CHOICE_BOX_ON_ERROR_DETECTION_STYLE;
import static com.cherryframe.cherryframe.storefront.constants.CPMSControllerConstants.ValidationStyles.UNABLE_TO_RESET_PASSWORD_STYLE;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


public class CPMSManagementStudioTabPaneController implements Initializable {

    @FXML
    private Button importButton;
    @FXML
    private ImageView importIcon;
    @FXML
    private TextArea infoTextArea;
    @FXML
    private Label accountUID, resetPassInfoLabel, changeWorkspaceInfoLabel;
    @FXML
    private PasswordField newPassword, repeatedNewPassword;
    @FXML
    private TextField filePathArea, currentWorkspace;
    @FXML
    private CheckBox stockUidCheck, usdPurchasePriceCheck, usdSellPriceCheck, usdProductPriceCheck, sellPrice1Check,
            sellPrice2Check, sellPrice3Check, sellPrice4Check, purchasePrice1Check, purchasePrice2Check,
            purchasePrice3Check, purchasePrice4Check;
    @FXML
    private ChoiceBox<String> stockUidChoice, usdPurchasePriceChoice, usdSellPriceChoice, usdProductPriceChoice, sellPrice1Choice,
            sellPrice2Choice, sellPrice3Choice, sellPrice4Choice, purchasePrice1Choice, purchasePrice2Choice,
            purchasePrice3Choice, purchasePrice4Choice, workspaceChoice;

    private final List<ChoiceBox<String>> availableChoiceBoxes = new ArrayList<>();
    private final List<CheckBox> availableCheckBoxes = new ArrayList<>();

    private final CPMSFileService CPMSFileService = new CPMSFileServiceImpl();
    private final CPMSUserService cpmsUserService = new CPMSUserServiceImpl();
    private final CPMSSessionService cpmsSessionService = new CPMSSessionServiceImpl();
    private final CPMSValidator<ResetPasswordForm> resetPasswordCPMSValidator = new CPMSResetPasswordValidator();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize all available choice/check boxes into a list to reuse.
        initializeChoiceBoxList();
        initializeCheckBoxList();
        initWorkspaceChoices();
        initFieldsOfSession();
    }

    @FXML
    protected void uploadFile() throws IOException {
        final var isFileAssigned = nonNull(filePathArea.getText()) && !filePathArea.getText().isEmpty();
        infoTextArea.setVisible(false);
        // assign empty body
        if (isFileAssigned) {
            filePathArea.setText(null);
        } else {
            // initialize FileChooser
            CPMSFileService.initializeFileChooser(filePathArea);
            // fill selection options & if not selected -> remove from available choice box list
            for (int index = 0; index < availableCheckBoxes.size(); index++) {
                fillAvailableChoiceBoxOptions(availableChoiceBoxes.get(index));
            }
            // unlock import button
            importButton.setDisable(false);
            importIcon.setDisable(false);
        }
    }

    @FXML
    protected void handleCheckboxOnClick() {
        infoTextArea.setVisible(false);
        // iterate and adjust visibility
        for (int i = 0; i < availableCheckBoxes.size(); i++) {
            adjustDropdownVisibility(availableCheckBoxes.get(i), availableChoiceBoxes.get(i));
        }
    }

    @FXML
    protected void importFromFile() {
        final var errorDetection = validateSelection();
        isInErrorDetection(errorDetection);
        if (!filePathArea.getText().isEmpty() && !errorDetection) {
            // Read & Initialize Sheet
            try {
                if (!stockUidCheck.isSelected()) {
                    throw new IllegalStateException(MANDATORY_STOCK_CODE_CHECK);
                }
                final Sheet sheet = CPMSFileService.readSingleExcelFile(filePathArea.getText());
                CPMSFileService.initializeAndImportFromFile(sheet, availableCheckBoxes, availableChoiceBoxes, infoTextArea);
                refreshImporter();
            } catch (final Exception e) {
                infoTextArea.setVisible(true);
                infoTextArea.setText(e.getMessage());
            }
        }
    }

    @FXML
    protected void resetPassword() {
        final var resetPasswordForm = new ResetPasswordForm();
        resetPasswordForm.setNewPass(newPassword.getText());
        resetPasswordForm.setRepeatedNewPass(repeatedNewPassword.getText());
        final var hasError = !resetPasswordCPMSValidator.validate(resetPasswordForm);
        final var prevResetPassStyle = newPassword.getStyle();
        if (hasError) {
            resetPassInfoLabel.setVisible(true);
            newPassword.setStyle(UNABLE_TO_RESET_PASSWORD_STYLE);
            repeatedNewPassword.setStyle(UNABLE_TO_RESET_PASSWORD_STYLE);
            resetPassInfoLabel.setText(UNABLE_TO_RESET_PASSWORD);
            makeLabelAsInvisibleAfterPeriod(resetPassInfoLabel, 5);
            newPassword.setStyle(prevResetPassStyle);
            repeatedNewPassword.setStyle(prevResetPassStyle);
        } else {
            cpmsUserService.resetPassword(newPassword.getText());
            resetPassInfoLabel.setVisible(true);
            resetPassInfoLabel.setTextFill(Color.web("green"));
            resetPassInfoLabel.setText(SUCCESSFULLY_RESET_PASSWORD);
            makeLabelAsInvisibleAfterPeriod(resetPassInfoLabel, 4);
            resetPassInfoLabel.setTextFill(Color.web("red"));
        }
    }

    @FXML
    protected void changeCurrentWorkspace() {
        final var currentUser = cpmsUserService.getCurrentUser();
        currentUser.setUserWorkspace(workspaceChoice.getValue());
        currentWorkspace.setText(workspaceChoice.getValue());
        changeWorkspaceInfoLabel.setVisible(true);
        changeWorkspaceInfoLabel.setText(SUCCESSFULLY_CHANGED_WORKSPACE);
        makeLabelAsInvisibleAfterPeriod(changeWorkspaceInfoLabel, 4);
    }

    @FXML
    protected void logout() throws IOException {
        cpmsSessionService.clearSession();
        changeScene(LOGIN_PAGE_UID);
    }

    private void adjustDropdownVisibility(final CheckBox checkBox, final ChoiceBox<String> choiceBox) {
        choiceBox.setVisible(checkBox.isSelected());
    }

    private void fillAvailableChoiceBoxOptions(final ChoiceBox<String> choiceBox) throws IOException {
        final var filePath = filePathArea.getText();
        if (nonNull(filePath) && !filePath.isEmpty()) {
            final Sheet sheet = CPMSFileService.readSingleExcelFile(filePath);
            for (final Row row : sheet) {
                if (row.getRowNum() == 0) {
                    if (choiceBox.getItems().size() == 0) {
                        row.forEach(cell -> choiceBox.getItems().add(cell.getStringCellValue()));
                    }
                }
                break;
            }
        }
    }

    private void initializeCheckBoxList() {
        availableCheckBoxes.clear();
        availableCheckBoxes.addAll(List.of(stockUidCheck, usdPurchasePriceCheck, usdSellPriceCheck, usdProductPriceCheck,
                sellPrice1Check, sellPrice2Check, sellPrice3Check, sellPrice4Check, purchasePrice1Check, purchasePrice2Check,
                purchasePrice3Check, purchasePrice4Check));
    }

    private void initializeChoiceBoxList() {
        availableChoiceBoxes.clear();
        availableChoiceBoxes.addAll(List.of(stockUidChoice, usdPurchasePriceChoice,
                usdSellPriceChoice, usdProductPriceChoice, sellPrice1Choice, sellPrice2Choice, sellPrice3Choice,
                sellPrice4Choice, purchasePrice1Choice, purchasePrice2Choice, purchasePrice3Choice,
                purchasePrice4Choice));
    }

    private boolean validateSelection() {
        boolean errorDetection = false;
        for (int i = 0; i < this.availableCheckBoxes.size(); i++) {
            if (this.availableCheckBoxes.get(i).isSelected()) {
                final var choiceBox = this.availableChoiceBoxes.get(i);
                if (isNull(choiceBox.getValue())) {
                    errorDetection = true;
                    adjustChoiceBoxStyle(choiceBox);
                }
            }
        }
        return errorDetection;
    }

    private void isInErrorDetection(boolean errorDetection) {
        if (errorDetection) {
            infoTextArea.setVisible(true);
            infoTextArea.setText(UNABLE_TO_IMPORT_DATA_INTO_DATABASE);
        }
    }

    private void adjustChoiceBoxStyle(final ChoiceBox<String> choiceBox) {
        choiceBox.setStyle(CHOICE_BOX_ON_ERROR_DETECTION_STYLE);
    }

    private void refreshImporter() {
        for (int i = 0; i < availableCheckBoxes.size(); i++) {
            availableCheckBoxes.get(i).setSelected(false);
            availableChoiceBoxes.get(i).setValue(null);
            adjustDropdownVisibility(availableCheckBoxes.get(i), availableChoiceBoxes.get(i));
        }
        filePathArea.setText(null);
        importButton.setDisable(true);
        importIcon.setDisable(true);
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

    private void initFieldsOfSession() {
        final var currentUser = cpmsUserService.getCurrentUser();
        if (nonNull(currentUser)) {
            accountUID.setText(currentUser.getUserUID());
            workspaceChoice.setValue(currentUser.getUserWorkspace());
            currentWorkspace.setText(currentUser.getUserWorkspace());
        }
    }

    private void makeLabelAsInvisibleAfterPeriod(final Label label, int period) {
        final PauseTransition pauseTransition = new PauseTransition(Duration.seconds(period));
        pauseTransition.setOnFinished(e -> label.setVisible(false));
        pauseTransition.play();
    }
}

