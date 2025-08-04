package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.model.Course;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterCourseController implements Initializable {
    
    @FXML
    private GridPane coursesGrid;
    
    private List<CheckBox> courseCheckBoxes;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        courseCheckBoxes = new ArrayList<>();
        loadAvailableCourses();
    }
    
    private void loadAvailableCourses() {
        coursesGrid.getChildren().clear();
        courseCheckBoxes.clear();
        
        List<Course> availableCourses = DataService.getAvailableCourses();
        List<Course> registeredCourses = DataService.getRegisteredCourses();
        
        int columns = 2;
        int row = 0;
        int col = 0;
        
        for (Course course : availableCourses) {
            VBox courseCard = createCourseCard(course, registeredCourses.contains(course));
            coursesGrid.add(courseCard, col, row);
            
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
        }
    }
    
    private VBox createCourseCard(Course course, boolean isRegistered) {
        VBox card = new VBox(15);
        card.getStyleClass().add("course-selection-card");
        card.setPadding(new Insets(20));
        card.setPrefWidth(350);
        card.setPrefHeight(180);
        
        // Checkbox
        CheckBox checkBox = new CheckBox();
        checkBox.setUserData(course);
        checkBox.setSelected(isRegistered);
        checkBox.getStyleClass().add("course-checkbox");
        courseCheckBoxes.add(checkBox);
        
        // Course info
        VBox courseInfo = new VBox(8);
        
        Label codeLabel = new Label(course.getCourseCode());
        codeLabel.getStyleClass().add("card-course-code");
        
        Label nameLabel = new Label(course.getCourseName());
        nameLabel.getStyleClass().add("card-course-name");
        nameLabel.setWrapText(true);
        
        Label instructorLabel = new Label("Prof. " + course.getInstructor());
        instructorLabel.getStyleClass().add("card-course-instructor");
        
        VBox detailsBox = new VBox(3);
        Label creditsLabel = new Label("Credits: " + course.getCredits());
        creditsLabel.getStyleClass().add("card-course-detail");
        
        Label scheduleLabel = new Label(course.getSchedule());
        scheduleLabel.getStyleClass().add("card-course-detail");
        scheduleLabel.setWrapText(true);
        
        detailsBox.getChildren().addAll(creditsLabel, scheduleLabel);
        
        courseInfo.getChildren().addAll(codeLabel, nameLabel, instructorLabel, detailsBox);
        
        card.getChildren().addAll(checkBox, courseInfo);
        return card;
    }
    
    @FXML
    private void handleRegister() {
        List<Course> selectedCourses = new ArrayList<>();
        
        for (CheckBox checkBox : courseCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedCourses.add((Course) checkBox.getUserData());
            }
        }
        
        if (selectedCourses.isEmpty()) {
            showAlert("No Selection", "Please select at least one course to register.");
            return;
        }
        
        // Clear current registrations and add selected ones
        List<Course> currentRegistered = new ArrayList<>(DataService.getRegisteredCourses());
        for (Course course : currentRegistered) {
            DataService.unregisterCourse(course);
        }
        
        for (Course course : selectedCourses) {
            DataService.registerCourse(course);
        }
        
        showAlert("Success", "Course registration updated successfully!\nRegistered for " + 
                 selectedCourses.size() + " course(s).");
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
