package com.student.registration.service;

import com.student.registration.model.Course;
import com.student.registration.model.Student;
import com.student.registration.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataService {
    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + "/users.txt";
    private static final String STUDENTS_FILE = DATA_DIR + "/students.txt";
    private static final String COURSES_FILE = DATA_DIR + "/courses.txt";
    
    private static Map<String, User> users = new HashMap<>();
    private static Map<String, Student> students = new HashMap<>();
    private static List<Course> availableCourses;
    private static String currentUsername;
    
    static {
        initializeData();
    }
    
    private static void ensureDataDirectoryExists() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }
    
    private static void initializeData() {
        ensureDataDirectoryExists();
        // Initialize available programming language courses
        availableCourses = new ArrayList<>();
        availableCourses.add(new Course("JAVA101", "Java Programming Fundamentals", "Prof. Rukesh", 4, "Mon/Wed 9:00-11:00"));
        availableCourses.add(new Course("PYTHON101", "Python Programming", "Dr. Vishal", 3, "Tue/Thu 10:00-11:30"));
        availableCourses.add(new Course("JS101", "JavaScript & Web Development", "Prof. Subit", 3, "Mon/Wed 2:00-3:30"));
        availableCourses.add(new Course("CPP101", "C++ Programming", "Dr. Bidhaan", 4, "Tue/Thu 1:00-2:30"));
        availableCourses.add(new Course("CSHARP101", "C# and .NET Development", "Prof. Anmol", 4, "Wed/Fri 11:00-12:30"));
        availableCourses.add(new Course("GO101", "Go Programming Language", "Dr. Bishal", 3, "Mon/Fri 3:00-4:30"));
        availableCourses.add(new Course("RUST101", "Rust Systems Programming", "Prof. Sachin", 4, "Tue/Thu 9:00-10:30"));
        availableCourses.add(new Course("SWIFT101", "Swift iOS Development", "Dr. Kevin", 3, "Wed/Fri 1:00-2:30"));
        availableCourses.add(new Course("KOTLIN101", "Kotlin Android Development", "Prof. Lily", 3, "Mon/Wed 11:00-12:30"));
        availableCourses.add(new Course("PHP101", "PHP Web Development", "Dr. Manil", 3, "Tue/Thu 3:00-4:30"));
        availableCourses.add(new Course("RUBY101", "Ruby on Rails", "Prof. Suraj", 3, "Wed/Fri 9:00-10:30"));
        availableCourses.add(new Course("SQL101", "Database Programming with SQL", "Dr. Dipshan", 3, "Mon/Wed 1:00-2:30"));
        
        // Load data from files if they exist
        loadUsersData();
        loadStudentsData();
        loadCoursesData();
    }
    
    public static boolean registerUser(String username, String password, String fullName, String email, 
                                     String dateOfBirth, String phoneNumber, String gender, String nationality, String address) {
        if (users.containsKey(username)) {
            return false; // Username already exists
        }
        
        User user = new User(username, password, fullName, email, dateOfBirth, phoneNumber, gender, nationality, address);
        users.put(username, user);
        
        Student student = new Student(fullName, email, "", ""); // Program and semester will be set later
        students.put(username, student);
        
        saveUsersData();
        saveStudentsData();
        return true;
    }
    
    public static boolean validateLogin(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }
    
    public static void setCurrentUser(String username) {
        currentUsername = username;
    }
    
    public static Student getCurrentStudent() {
        return students.get(currentUsername);
    }
    
    public static User getCurrentUser() {
        return users.get(currentUsername);
    }
    
    public static void updateStudent(Student student) {
        students.put(currentUsername, student);
        saveStudentsData();
    }
    
    public static void updateUser(User user) {
        users.put(currentUsername, user);
        saveUsersData();
    }
    
    public static List<Course> getAvailableCourses() {
        return new ArrayList<>(availableCourses);
    }
    
    public static void registerCourse(Course course) {
        Student student = getCurrentStudent();
        if (student != null) {
            student.addCourse(course);
            saveStudentsData();
        }
    }
    
    public static void unregisterCourse(Course course) {
        Student student = getCurrentStudent();
        if (student != null) {
            student.removeCourse(course);
            saveStudentsData();
        }
    }
    
    public static List<Course> getRegisteredCourses() {
        Student student = getCurrentStudent();
        return student != null ? student.getRegisteredCourses() : new ArrayList<>();
    }
    
    private static void saveUsersData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users.values()) {
                writer.println(formatUserData(user));
            }
        } catch (IOException e) {
            System.err.println("Error saving users data: " + e.getMessage());
        }
    }
    
    private static void loadUsersData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUserData(line);
                if (user != null) {
                    users.put(user.getUsername(), user);
                }
            }
        } catch (IOException e) {
            System.out.println("No existing users data found, starting fresh");
        }
    }
    
    private static void saveStudentsData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Map.Entry<String, Student> entry : students.entrySet()) {
                writer.println(formatStudentData(entry.getKey(), entry.getValue()));
            }
        } catch (IOException e) {
            System.err.println("Error saving students data: " + e.getMessage());
        }
    }
    
    private static void loadStudentsData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseAndAddStudentData(line);
            }
        } catch (IOException e) {
            System.out.println("No existing students data found, starting fresh");
        }
    }
    
    private static void saveCoursesData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            for (Course course : availableCourses) {
                writer.println(formatCourseData(course));
            }
        } catch (IOException e) {
            System.err.println("Error saving courses data: " + e.getMessage());
        }
    }
    
    private static void loadCoursesData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            List<Course> loadedCourses = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                Course course = parseCourseData(line);
                if (course != null) {
                    loadedCourses.add(course);
                }
            }
            if (!loadedCourses.isEmpty()) {
                availableCourses = loadedCourses;
            }
        } catch (IOException e) {
            System.out.println("Using default courses data");
        }
    }

    private static String formatUserData(User user) {
        return String.join("|", 
            escapeString(user.getUsername()),
            escapeString(user.getPassword()),
            escapeString(user.getFullName()),
            escapeString(user.getEmail()),
            escapeString(user.getDateOfBirth()),
            escapeString(user.getPhoneNumber()),
            escapeString(user.getGender()),
            escapeString(user.getNationality()),
            escapeString(user.getAddress()),
            escapeString(user.getProgram()),
            escapeString(user.getSemester())
        );
    }
    
    private static User parseUserData(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 9) {
                User user = new User(
                    unescapeString(parts[0]), // username
                    unescapeString(parts[1]), // password
                    unescapeString(parts[2]), // fullName
                    unescapeString(parts[3]), // email
                    unescapeString(parts[4]), // dateOfBirth
                    unescapeString(parts[5]), // phoneNumber
                    unescapeString(parts[6]), // gender
                    unescapeString(parts[7]), // nationality
                    unescapeString(parts[8])  // address
                );
                if (parts.length > 9) user.setProgram(unescapeString(parts[9]));
                if (parts.length > 10) user.setSemester(unescapeString(parts[10]));
                return user;
            }
        } catch (Exception e) {
            System.err.println("Error parsing user data: " + line);
        }
        return null;
    }
    
    private static String formatStudentData(String username, Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append(escapeString(username)).append("|");
        sb.append(escapeString(student.getName())).append("|");
        sb.append(escapeString(student.getEmail())).append("|");
        sb.append(escapeString(student.getProgram())).append("|");
        sb.append(escapeString(student.getSemester())).append("|");
        
        // Add registered courses
        List<Course> courses = student.getRegisteredCourses();
        sb.append(courses.size()).append("|");
        for (Course course : courses) {
            sb.append(formatCourseData(course)).append("~");
        }
        
        return sb.toString();
    }
    
    private static void parseAndAddStudentData(String line) {
        try {
            String[] parts = line.split("\\|", -1);
            if (parts.length >= 6) {
                String username = unescapeString(parts[0]);
                Student student = new Student(
                    unescapeString(parts[1]), // name
                    unescapeString(parts[2]), // email
                    unescapeString(parts[3]), // program
                    unescapeString(parts[4])  // semester
                );
                
                // Parse registered courses
                int courseCount = Integer.parseInt(parts[5]);
                if (parts.length > 6 && !parts[6].isEmpty()) {
                    String[] courseStrings = parts[6].split("~");
                    for (int i = 0; i < Math.min(courseCount, courseStrings.length); i++) {
                        Course course = parseCourseData(courseStrings[i]);
                        if (course != null) {
                            student.addCourse(course);
                        }
                    }
                }
                
                students.put(username, student);
            }
        } catch (Exception e) {
            System.err.println("Error parsing student data: " + line);
        }
    }
    
    private static String formatCourseData(Course course) {
        return String.join(";",
            escapeString(course.getCourseCode()),
            escapeString(course.getCourseName()),
            escapeString(course.getInstructor()),
            String.valueOf(course.getCredits()),
            escapeString(course.getSchedule())
        );
    }
    
    private static Course parseCourseData(String line) {
        try {
            String[] parts = line.split(";", -1);
            if (parts.length >= 5) {
                return new Course(
                    unescapeString(parts[0]), // courseCode
                    unescapeString(parts[1]), // courseName
                    unescapeString(parts[2]), // instructor
                    Integer.parseInt(parts[3]), // credits
                    unescapeString(parts[4])  // schedule
                );
            }
        } catch (Exception e) {
            System.err.println("Error parsing course data: " + line);
        }
        return null;
    }
    
    private static String escapeString(String str) {
        if (str == null) return "";
        return str.replace("|", "\\|").replace(";", "\\;").replace("~", "\\~");
    }
    
    private static String unescapeString(String str) {
        if (str == null || str.isEmpty()) return "";
        return str.replace("\\|", "|").replace("\\;", ";").replace("\\~", "~");
    }
}
