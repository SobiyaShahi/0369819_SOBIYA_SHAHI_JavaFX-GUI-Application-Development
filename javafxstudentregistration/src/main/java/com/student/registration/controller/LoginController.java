package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }
        
        if (DataService.validateLogin(username, password)) {
            DataService.setCurrentUser(username);
            try {
                StudentRegistrationApp.showDashboardScene();
            } catch (Exception e) {
                showAlert("Error", "Failed to load dashboard: " + e.getMessage());
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.\nPlease check your credentials or register a new account.");
        }
    }
    
    @FXML
    private void handleRegister() {
        try {
            StudentRegistrationApp.showRegisterScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to load registration page: " + e.getMessage());
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
