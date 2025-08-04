package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class RegisterController {
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    
    @FXML
    private PasswordField confirmPasswordField;
    
    @FXML
    private TextField fullNameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private DatePicker dobField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private ComboBox<String> genderComboBox;
    
    @FXML
    private TextField nationalityField;
    
    @FXML
    private TextArea addressField;
    
    @FXML
    private void initialize() {
        // Initialize gender options
        genderComboBox.getItems().addAll(
            "Male",
            "Female",
            "Other",
            "Prefer not to say"
        );
    }
    
    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String fullName = fullNameField.getText().trim();
        String email = emailField.getText().trim();
        LocalDate dob = dobField.getValue();
        String phone = phoneField.getText().trim();
        String gender = genderComboBox.getValue();
        String nationality = nationalityField.getText().trim();
        String address = addressField.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || 
            email.isEmpty() || dob == null || phone.isEmpty() || 
            gender == null || nationality.isEmpty() || address.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }
        
        if (username.length() < 3) {
            showAlert("Error", "Username must be at least 3 characters long.");
            return;
        }
        
        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters long.");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }
        
        if (!email.contains("@") || !email.contains(".")) {
            showAlert("Error", "Please enter a valid email address.");
            return;
        }
        
        if (phone.length() < 10) {
            showAlert("Error", "Please enter a valid phone number.");
            return;
        }
        
        // Check if user is at least 16 years old
        if (dob.isAfter(LocalDate.now().minusYears(16))) {
            showAlert("Error", "You must be at least 16 years old to register.");
            return;
        }
        
        // Try to register user with personal information
        if (DataService.registerUser(username, password, fullName, email, dob.toString(), phone, gender, nationality, address)) {
            showAlert("Success", "Registration successful! You can now login with your credentials.");
            try {
                StudentRegistrationApp.showLoginScene();
            } catch (Exception e) {
                showAlert("Error", "Failed to return to login: " + e.getMessage());
            }
        } else {
            showAlert("Error", "Username already exists. Please choose a different username.");
        }
    }
    
    @FXML
    private void handleBackToLogin() {
        try {
            StudentRegistrationApp.showLoginScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to return to login: " + e.getMessage());
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
