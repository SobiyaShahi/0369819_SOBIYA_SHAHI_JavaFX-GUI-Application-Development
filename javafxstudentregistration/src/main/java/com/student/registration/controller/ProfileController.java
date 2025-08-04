package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.model.Student;
import com.student.registration.model.User;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField emailField;
    
    @FXML
    private TextField dobField;
    
    @FXML
    private TextField phoneField;
    
    @FXML
    private TextField genderField;
    
    @FXML
    private TextField nationalityField;
    
    @FXML
    private TextArea addressField;
    
    @FXML
    private TextField programField;
    
    @FXML
    private TextField semesterField;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = DataService.getCurrentUser();
        Student student = DataService.getCurrentStudent();
        
        if (user != null) {
            nameField.setText(user.getFullName() != null ? user.getFullName() : "");
            emailField.setText(user.getEmail() != null ? user.getEmail() : "");
            dobField.setText(user.getDateOfBirth() != null ? user.getDateOfBirth() : "");
            phoneField.setText(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
            genderField.setText(user.getGender() != null ? user.getGender() : "");
            nationalityField.setText(user.getNationality() != null ? user.getNationality() : "");
            addressField.setText(user.getAddress() != null ? user.getAddress() : "");
        }
        
        if (student != null) {
            programField.setText(student.getProgram() != null ? student.getProgram() : "");
            semesterField.setText(student.getSemester() != null ? student.getSemester() : "");
        }
    }
    
    @FXML
    private void handleSave() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dob = dobField.getText().trim();
        String phone = phoneField.getText().trim();
        String gender = genderField.getText().trim();
        String nationality = nationalityField.getText().trim();
        String address = addressField.getText().trim();
        String program = programField.getText().trim();
        String semester = semesterField.getText().trim();
        
        if (name.isEmpty() || email.isEmpty()) {
            showAlert("Error", "Name and email are required fields.");
            return;
        }
        
        // Update User information
        User user = DataService.getCurrentUser();
        if (user != null) {
            user.setFullName(name);
            user.setEmail(email);
            user.setDateOfBirth(dob);
            user.setPhoneNumber(phone);
            user.setGender(gender);
            user.setNationality(nationality);
            user.setAddress(address);
            user.setProgram(program);
            user.setSemester(semester);
            DataService.updateUser(user);
        }
        
        // Update Student information
        Student student = DataService.getCurrentStudent();
        if (student != null) {
            student.setName(name);
            student.setEmail(email);
            student.setProgram(program);
            student.setSemester(semester);
            DataService.updateStudent(student);
        }
        
        showAlert("Success", "Profile updated successfully!");
    }
    
    @FXML
    private void handleBack() {
        try {
            StudentRegistrationApp.showDashboardScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to return to dashboard: " + e.getMessage());
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
