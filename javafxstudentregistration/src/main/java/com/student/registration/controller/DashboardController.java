package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    
    @FXML
    private Label welcomeLabel;
    
    @FXML
    private Label statsLabel;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String studentName = DataService.getCurrentStudent().getName();
        welcomeLabel.setText("Welcome, " + studentName + "!");
        
        int registeredCourses = DataService.getRegisteredCourses().size();
        int availableCourses = DataService.getAvailableCourses().size();
        statsLabel.setText("Registered Courses: " + registeredCourses + " | Available Courses: " + availableCourses);
    }
    
    @FXML
    private void handleProfile() {
        try {
            StudentRegistrationApp.showProfileScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to load profile: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleRegisterCourse() {
        try {
            StudentRegistrationApp.showRegisterCourseScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to load course registration: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleViewCourses() {
        try {
            StudentRegistrationApp.showViewCoursesScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to load courses view: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleChart() {
        try {
            StudentRegistrationApp.showChartScene();
        } catch (Exception e) {
            showAlert("Error", "Failed to load chart: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("You will be redirected to the login page.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                StudentRegistrationApp.showLoginScene();
            } catch (Exception e) {
                showAlert("Error", "Failed to logout: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Application");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("The application will be closed.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
