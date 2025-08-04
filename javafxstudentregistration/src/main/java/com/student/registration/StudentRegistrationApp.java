package com.student.registration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;

public class    StudentRegistrationApp extends Application {
    
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Student Course Registration System");
        primaryStage.setResizable(true);
        
        // Create data directory if it doesn't exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Load login scene
        showLoginScene();
        
        primaryStage.show();
    }
    
    public static void showLoginScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 650);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showRegisterScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/Register.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 600, 750);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showDashboardScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/Dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 750);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showProfileScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/Profile.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 700, 600);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showRegisterCourseScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/RegisterCourse.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 650);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showViewCoursesScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/ViewCourses.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static void showChartScene() throws Exception {
        FXMLLoader loader = new FXMLLoader(StudentRegistrationApp.class.getResource("/com/student/registration/fxml/Chart.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(StudentRegistrationApp.class.getResource("/com/student/registration/css/style.css").toExternalForm());
        primaryStage.setScene(scene);
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
