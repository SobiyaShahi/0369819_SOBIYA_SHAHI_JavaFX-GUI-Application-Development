package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.model.Course;
import com.student.registration.service.DataService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewCoursesController implements Initializable {
    
    @FXML
    private VBox coursesContainer;
    
    @FXML
    private Label totalCoursesLabel;
    
    @FXML
    private Label totalCreditsLabel;
    
    private Course selectedCourse;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadRegisteredCourses();
    }
    
    private void loadRegisteredCourses() {
        coursesContainer.getChildren().clear();
        List<Course> registeredCourses = DataService.getRegisteredCourses();
        
        for (Course course : registeredCourses) {
            VBox courseCard = createCourseCard(course);
            coursesContainer.getChildren().add(courseCard);
        }
        
        updateStatistics();
    }
    
    private VBox createCourseCard(Course course) {
        VBox card = new VBox(10);
        card.getStyleClass().add("course-card");
        card.setPadding(new Insets(20));
        
        // Course header
        HBox header = new HBox(10);
        header.getStyleClass().add("course-header");
        
        Label codeLabel = new Label(course.getCourseCode());
        codeLabel.getStyleClass().add("course-code");
        
        Label nameLabel = new Label(course.getCourseName());
        nameLabel.getStyleClass().add("course-name");
        
        header.getChildren().addAll(codeLabel, nameLabel);
        
        // Course details
        VBox details = new VBox(5);
        
        Label instructorLabel = new Label("Instructor: " + course.getInstructor());
        instructorLabel.getStyleClass().add("course-detail");
        
        Label creditsLabel = new Label("Credits: " + course.getCredits());
        creditsLabel.getStyleClass().add("course-detail");
        
        Label scheduleLabel = new Label("Schedule: " + course.getSchedule());
        scheduleLabel.getStyleClass().add("course-detail");
        
        details.getChildren().addAll(instructorLabel, creditsLabel, scheduleLabel);
        
        // Selection checkbox
        CheckBox selectBox = new CheckBox("Select for deletion");
        selectBox.getStyleClass().add("course-select");
        selectBox.setOnAction(e -> {
            if (selectBox.isSelected()) {
                selectedCourse = course;
                // Uncheck other checkboxes
                coursesContainer.getChildren().forEach(node -> {
                    if (node instanceof VBox && node != card) {
                        VBox otherCard = (VBox) node;
                        otherCard.getChildren().forEach(child -> {
                            if (child instanceof CheckBox && child != selectBox) {
                                ((CheckBox) child).setSelected(false);
                            }
                        });
                    }
                });
            } else {
                selectedCourse = null;
            }
        });
        
        card.getChildren().addAll(header, details, selectBox);
        return card;
    }
    
    private void updateStatistics() {
        List<Course> courses = DataService.getRegisteredCourses();
        int totalCourses = courses.size();
        int totalCredits = courses.stream().mapToInt(Course::getCredits).sum();
        
        totalCoursesLabel.setText(String.valueOf(totalCourses));
        totalCreditsLabel.setText(String.valueOf(totalCredits));
    }
    
    @FXML
    private void handleDeleteCourse() {
        if (selectedCourse == null) {
            showAlert("No Selection", "Please select a course to delete.");
            return;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Course");
        alert.setHeaderText("Are you sure you want to delete this course?");
        alert.setContentText("Course: " + selectedCourse.getCourseCode() + " - " + selectedCourse.getCourseName());
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DataService.unregisterCourse(selectedCourse);
            selectedCourse = null;
            loadRegisteredCourses();
            showAlert("Success", "Course deleted successfully!");
        }
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
