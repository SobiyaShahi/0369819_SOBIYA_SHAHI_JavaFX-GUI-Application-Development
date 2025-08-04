package com.student.registration.controller;

import com.student.registration.StudentRegistrationApp;
import com.student.registration.model.Course;
import com.student.registration.service.DataService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private Label summaryLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadChartData();
    }

    private void loadChartData() {
        List<Course> registeredCourses = DataService.getRegisteredCourses();

        if (registeredCourses.isEmpty()) {
            summaryLabel.setText("No courses registered yet.");
            return;
        }

        // === PIE CHART (Individual Courses) ===
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Course course : registeredCourses) {
            String label = course.getCourseCode() + " (" + course.getCredits() + " cr)";
            pieChartData.add(new PieChart.Data(label, course.getCredits())); // Credit hours as value
        }

        pieChart.setData(pieChartData);
        pieChart.setTitle("Credits per Course");

        // === BAR CHART (Credits by Category) ===
        Map<String, Integer> creditsByCategory = new HashMap<>();
        for (Course course : registeredCourses) {
            String category = getCourseCategory(course.getCourseCode());
            creditsByCategory.put(category, creditsByCategory.getOrDefault(category, 0) + course.getCredits());
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Credits by Category");

        for (Map.Entry<String, Integer> entry : creditsByCategory.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.setTitle("Credit Hours by Category");

        // === SUMMARY ===
        int totalCourses = registeredCourses.size();
        int totalCredits = registeredCourses.stream().mapToInt(Course::getCredits).sum();
        summaryLabel.setText("Total Registered: " + totalCourses + " courses, " + totalCredits + " credits");
    }

    private String getCourseCategory(String courseCode) {
        if (courseCode.startsWith("CS")) {
            return "Computer Science";
        } else if (courseCode.startsWith("MATH")) {
            return "Mathematics";
        } else if (courseCode.startsWith("ENG")) {
            return "English";
        } else if (courseCode.startsWith("PHYS")) {
            return "Physics";
        } else if (courseCode.startsWith("CHEM")) {
            return "Chemistry";
        } else {
            return "Other";
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
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
