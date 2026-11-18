package hr.algebra.kompassgo.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the registration screen (M1-01, SCRUM-9).
 *
 * <p>This class only wires the FXML nodes so the screen can be shown and worked on.
 * The behaviour behind the form belongs to other subtasks of SCRUM-7:
 * field validation and the messages under each field (SCRUM-10),
 * the e-mail check and saving through UserRepository (SCRUM-11),
 * and password hashing with jBCrypt.
 *
 * <p>The *ErrorLabel nodes are already in the FXML, hidden and unmanaged.
 * Validation shows one by setting its text and then both visible and managed to true,
 * so the layout does not shift while the field is still valid.
 */
public class RegisterController {

    private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField repeatPasswordField;

    @FXML private Label firstNameErrorLabel;
    @FXML private Label lastNameErrorLabel;
    @FXML private Label emailErrorLabel;
    @FXML private Label passwordErrorLabel;
    @FXML private Label repeatPasswordErrorLabel;

    @FXML private Button registerButton;
    @FXML private Hyperlink loginLink;

    /** Called by FXMLLoader once every @FXML field is injected. */
    @FXML
    private void initialize() {
        firstNameField.requestFocus();
        log.info("Registration screen loaded");
    }
}