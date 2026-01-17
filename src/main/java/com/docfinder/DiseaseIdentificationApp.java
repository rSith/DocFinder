package com.docfinder;

import com.docfinder.dao.UserDAO;
import com.docfinder.dao.DoctorDAO;
import com.docfinder.dao.DiseaseDAO;
import com.docfinder.model.User;
import com.docfinder.model.Disease;
import com.docfinder.model.Doctor;
import com.docfinder.service.SymptomChecker;
import com.docfinder.util.PasswordHasher;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javafx.animation.PauseTransition; // Essential for the loading delay
import javafx.application.Application;
import javafx.application.Platform;      // Essential for thread safety
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;             // Essential for the timer

import java.util.ArrayList;
import java.util.List;

public class DiseaseIdentificationApp extends Application {

    private UserDAO userDAO = new UserDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private DiseaseDAO diseaseDAO = new DiseaseDAO();
    private SymptomChecker symptomChecker = new SymptomChecker();

    private Stage window;
    private Scene mainScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("DocFinder - Health System");
        showLoginScreen();
        window.setMaximized(true);
        window.show();
    }

    // =============================================================
    // 🎨 LAYOUT HELPER
    // =============================================================
    private void switchScene(Node contentBox) {
        StackPane root = new StackPane();
        root.getStyleClass().add("main-root");
        root.getChildren().add(contentBox);

        // Fade In Animation
        javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(Duration.millis(500), contentBox);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        if (mainScene == null) {
            mainScene = new Scene(root, 1280, 720);
            if (getClass().getResource("/style.css") != null) {
                mainScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            }
            window.setScene(mainScene);
        } else {
            mainScene.setRoot(root);
        }
        window.setMaximized(true);
    }

    // ==========================================
    // SCREEN 1: LOGIN
    // ==========================================
    private void showLoginScreen() {
        VBox leftPane = createLeftBrandingPane();

        VBox rightPane = new VBox();
        rightPane.getStyleClass().add("right-form-pane");
        rightPane.setAlignment(Pos.CENTER);
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        VBox loginCard = new VBox(20);
        loginCard.getStyleClass().add("login-card-container");
        loginCard.setMaxWidth(400);

        Label loginHeader = new Label("Welcome Back");
        loginHeader.getStyleClass().add("login-header-text");

        HBox userContainer = new HBox(10);
        userContainer.getStyleClass().add("input-group");
        userContainer.setAlignment(Pos.CENTER_LEFT);
        Label userIcon = new Label("👤");
        userIcon.setStyle("-fx-text-fill: #888;");
        TextField userField = new TextField();
        userField.setPromptText("Username");
        userField.getStyleClass().add("modern-input-field");
        HBox.setHgrow(userField, Priority.ALWAYS);
        userContainer.getChildren().addAll(userIcon, userField);

        HBox passContainer = new HBox(10);
        passContainer.getStyleClass().add("input-group");
        passContainer.setAlignment(Pos.CENTER_LEFT);
        Label passIcon = new Label("🔒");
        passIcon.setStyle("-fx-text-fill: #888;");
        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.getStyleClass().add("modern-input-field");
        HBox.setHgrow(passField, Priority.ALWAYS);
        passContainer.getChildren().addAll(passIcon, passField);

        Button loginBtn = new Button("LOGIN");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.getStyleClass().add("button-primary-blue");

        Button regBtn = new Button("New user? Create an account");
        regBtn.getStyleClass().add("button-link-blue");

        Label msgLabel = new Label();
        msgLabel.getStyleClass().add("message-label");

        // Inside showLoginScreen()
        loginBtn.setOnAction(e -> {
            User user = userDAO.getUserByUsername(userField.getText());
            if (user != null && PasswordHasher.verify(passField.getText(), user.getPasswordHash())) {
                showDashboardScreen(user);
                // --- NEW: TOAST CALL ---
                showToast("Login Successful! Welcome, " + user.getName());
            } else {
                // Keep the red error message for failures, or use a toast here too
                msgLabel.setText("Invalid Username or Password");
                // Optional: showToast("Invalid Credentials");
            }
        });

        regBtn.setOnAction(e -> showRegistrationScreen());

        loginCard.getChildren().addAll(loginHeader, msgLabel, userContainer, passContainer, loginBtn, regBtn);
        rightPane.getChildren().add(loginCard);

        HBox splitRoot = new HBox(leftPane, rightPane);
        switchScene(splitRoot);
    }

    // ==========================================
    // SCREEN 2: REGISTRATION
    // ==========================================
    private void showRegistrationScreen() {
        VBox brandingPane = createLeftBrandingPane();

        VBox formPane = new VBox();
        formPane.getStyleClass().add("right-form-pane");
        formPane.setAlignment(Pos.CENTER);
        HBox.setHgrow(formPane, Priority.ALWAYS);

        VBox regCard = new VBox(15);
        regCard.getStyleClass().add("login-card-container");
        regCard.setMaxWidth(450);

        Label title = new Label("Create Account");
        title.getStyleClass().add("login-header-text");

        Label msgLabel = new Label();
        msgLabel.getStyleClass().add("message-label");

        TextField nameField = new TextField(); nameField.setPromptText("Full Name");
        nameField.getStyleClass().add("modern-input-field");

        TextField ageField = new TextField(); ageField.setPromptText("Age");
        ageField.getStyleClass().add("modern-input-field");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Male", "Female");
        genderBox.setPromptText("Gender");
        genderBox.setMaxWidth(Double.MAX_VALUE);
        genderBox.getStyleClass().add("modern-input-field");

        TextField contactField = new TextField(); contactField.setPromptText("Mobile Number");
        contactField.getStyleClass().add("modern-input-field");

        TextField userField = new TextField(); userField.setPromptText("Choose Username");
        userField.getStyleClass().add("modern-input-field");

        PasswordField passField = new PasswordField(); passField.setPromptText("Choose Password");
        passField.getStyleClass().add("modern-input-field");

        Button submitBtn = new Button("REGISTER NOW");
        submitBtn.setMaxWidth(Double.MAX_VALUE);
        submitBtn.getStyleClass().add("button-primary-blue");

        Button backBtn = new Button("Back to Login");
        backBtn.getStyleClass().add("button-link-blue");

        // Inside showRegistrationScreen()
        submitBtn.setOnAction(e -> {
            try {
                int age = Integer.parseInt(ageField.getText());
                String hash = PasswordHasher.hash(passField.getText());
                User u = new User(nameField.getText(), age, genderBox.getValue(), contactField.getText(), userField.getText(), hash);

                if(userDAO.registerUser(u)) {
                    showLoginScreen();
                    // --- NEW: TOAST CALL ---
                    showToast("Account created successfully! Please login.");
                } else {
                    msgLabel.setText("Username taken!");
                }
            } catch (Exception ex) {
                msgLabel.setText("Please check your inputs!");
            }
        });

        backBtn.setOnAction(e -> showLoginScreen());

        regCard.getChildren().addAll(title, msgLabel, nameField, ageField, genderBox, contactField, userField, passField, submitBtn, backBtn);
        formPane.getChildren().add(regCard);

        HBox splitRoot = new HBox(brandingPane, formPane);
        switchScene(splitRoot);
    }

    // ==========================================
    // SCREEN 3: DASHBOARD
    // ==========================================
    // ==========================================
    // SCREEN 3: DASHBOARD (Updated with Health Tip)
    // ==========================================
    // ==========================================
    // SCREEN 3: DASHBOARD (Professional & Dynamic)
    // ==========================================
    // ==========================================
    // SCREEN 3: DASHBOARD (Final Version)
    // ==========================================
    private void showDashboardScreen(User user) {
        VBox mainLayout = new VBox(30);
        mainLayout.getStyleClass().add("dashboard-background");
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // 1. Navbar
        HBox navbar = new HBox();
        navbar.getStyleClass().add("navbar");
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 40, 15, 40));

        Label brand = new Label("DocFinder");
        brand.getStyleClass().add("navbar-brand");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- PROFESSIONAL AVATAR LOGIC ---
        String initial = "";
        if (user.getName() != null && !user.getName().isEmpty()) {
            initial = user.getName().substring(0, 1).toUpperCase();
            if (user.getName().contains(" ")) {
                int spaceIndex = user.getName().indexOf(" ");
                if (spaceIndex + 1 < user.getName().length()) {
                    initial += user.getName().substring(spaceIndex + 1, spaceIndex + 2).toUpperCase();
                }
            }
        }

        javafx.scene.shape.Circle avatarCircle = new javafx.scene.shape.Circle(20);
        avatarCircle.setFill(javafx.scene.paint.Color.web("#e3f2fd"));
        avatarCircle.setStroke(javafx.scene.paint.Color.web("#0052D4"));
        avatarCircle.setStrokeWidth(2);

        Label initialsLabel = new Label(initial);
        initialsLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #0052D4; -fx-font-size: 14px;");

        StackPane avatarPane = new StackPane(avatarCircle, initialsLabel);

        Label userLabel = new Label(user.getName());
        userLabel.getStyleClass().add("navbar-user");
        userLabel.setPadding(new Insets(0, 15, 0, 10));

        Button btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("button-logout-small");

        // --- STEP 4 UPDATE: LOGOUT TOAST ---
        btnLogout.setOnAction(e -> {
            showLoginScreen();
            showToast("You have been logged out securely.");
        });

        navbar.getChildren().addAll(brand, spacer, avatarPane, userLabel, btnLogout);

        // 2. Hero Section
        VBox heroSection = new VBox(10);
        heroSection.setAlignment(Pos.CENTER);
        heroSection.setPadding(new Insets(40, 0, 10, 0));

        int hour = java.time.LocalTime.now().getHour();
        String greeting = "";
        if (hour < 12) greeting = "Good Morning";
        else if (hour < 18) greeting = "Good Afternoon";
        else greeting = "Good Evening";

        Label welcomeTitle = new Label(greeting + ", " + user.getName());
        welcomeTitle.getStyleClass().add("hero-title");

        String date = java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d"));
        Label welcomeSub = new Label("It's " + date + ". Let's check your health status.");
        welcomeSub.getStyleClass().add("hero-subtitle");

        heroSection.getChildren().addAll(welcomeTitle, welcomeSub);

        // 3. Random Daily Health Tip
        VBox tipBox = new VBox(10);
        tipBox.setStyle("-fx-background-color: linear-gradient(to right, #e0f7fa, #ffffff); " +
                "-fx-background-radius: 15; " +
                "-fx-padding: 20; " +
                "-fx-border-color: #b2ebf2; " +
                "-fx-border-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 10, 0, 0, 5);");
        tipBox.setMaxWidth(630);
        tipBox.setAlignment(Pos.CENTER_LEFT);

        Label tipTitle = new Label("💡 Daily Health Tip");
        tipTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #006064; -fx-font-size: 16px;");

        String[] tips = {
                "Stay hydrated! Drinking water boosts energy and brain function.",
                "Aim for 30 minutes of moderate exercise today to boost your immunity.",
                "Sleep is key! Adults need 7-9 hours of quality sleep for recovery.",
                "Reduce sugar intake to lower inflammation and improve energy.",
                "Take a deep breath. Stress management is vital for heart health.",
                "Eat more fiber! Vegetables and whole grains keep your gut healthy."
        };
        String randomTip = tips[(int) (Math.random() * tips.length)];

        Label tipText = new Label(randomTip);
        tipText.setWrapText(true);
        tipText.setStyle("-fx-text-fill: #00838f; -fx-font-size: 14px;");
        tipBox.getChildren().addAll(tipTitle, tipText);

        // 4. Action Cards
        HBox cardsContainer = new HBox(30);
        cardsContainer.setAlignment(Pos.CENTER);
        cardsContainer.setPadding(new Insets(20));

        VBox cardSymptoms = createActionCard("🤒", "Identify My Disease", "Enter your symptoms to get an instant preliminary diagnosis and first-aid advice.");
        cardSymptoms.setOnMouseClicked(e -> showSymptomCheckerScreen(user));

        VBox cardDoctor = createActionCard("👨‍⚕️", "Find a Specialist", "Locate top-rated doctors and specialists near you based on your condition.");
        cardDoctor.setOnMouseClicked(e -> showDoctorsScreen(user));

        cardsContainer.getChildren().addAll(cardSymptoms, cardDoctor);

        // --- PROFESSIONAL FOOTER ---
        VBox footer = new VBox(5);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(30, 0, 20, 0));

        Label copyright = new Label("© 2026 DocFinder Health System v1.0");
        copyright.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 12px; -fx-font-weight: bold;");

        Label credits = new Label("Precision Diagnostics & Expert Care Directory");
        credits.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 11px;");

        footer.getChildren().addAll(copyright, credits);

        // 5. Assemble Main Layout
        mainLayout.getChildren().addAll(navbar, heroSection, tipBox, cardsContainer, footer);

        ScrollPane scroll = new ScrollPane(mainLayout);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.getStyleClass().add("dashboard-scroll");

        switchScene(scroll);
    }
    // ==========================================
    // SCREEN 4: SYMPTOM CHECKER (FIXED LOADING)
    // ==========================================
    private void showSymptomCheckerScreen(User user) {
        VBox mainLayout = new VBox(25);
        mainLayout.getStyleClass().add("page-background");
        mainLayout.setPadding(new Insets(40));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER);
        Label title = new Label("Symptom Checker");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Select all symptoms you are experiencing for a preliminary analysis.");
        subtitle.getStyleClass().add("page-subtitle");
        headerBox.getChildren().addAll(title, subtitle);

        VBox contentCard = new VBox(20);
        contentCard.getStyleClass().add("action-card");
        contentCard.setMaxWidth(800);
        contentCard.setPadding(new Insets(30));

        Accordion accordion = new Accordion();
        accordion.getStyleClass().add("modern-accordion");

        TitledPane p1 = createCategoryPane("General & Respiratory");
        TitledPane p2 = createCategoryPane("Skin & External");
        TitledPane p3 = createCategoryPane("Other");

        List<CheckBox> allChecks = new ArrayList<>();
        List<String> symptoms = diseaseDAO.getAllSymptoms();

        for(String s : symptoms) {
            CheckBox cb = new CheckBox(s);
            cb.getStyleClass().add("symptom-checkbox");
            allChecks.add(cb);
            if(s.contains("Fever") || s.contains("Cough") || s.contains("Pain")) addToPane(p1, cb);
            else if(s.contains("Rash") || s.contains("Itch")) addToPane(p2, cb);
            else addToPane(p3, cb);
        }
        accordion.getPanes().addAll(p1, p2, p3);
        accordion.setExpandedPane(p1);

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Back to Dashboard");
        backBtn.getStyleClass().add("button-link-blue");
        backBtn.setOnAction(e -> showDashboardScreen(user));

        Button analyzeBtn = new Button("Analyze Symptoms");
        analyzeBtn.getStyleClass().add("button-primary-blue");
        analyzeBtn.setStyle("-fx-font-size: 16px; -fx-padding: 12 30;");

        buttonBox.getChildren().addAll(backBtn, analyzeBtn);
        contentCard.getChildren().addAll(accordion, buttonBox);
        mainLayout.getChildren().addAll(headerBox, contentCard);

        // --- LOADING EFFECT LOGIC ---
        analyzeBtn.setOnAction(e -> {
            List<String> selected = new ArrayList<>();
            for(CheckBox c : allChecks) if(c.isSelected()) selected.add(c.getText());

            if(selected.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Needed");
                alert.setHeaderText(null);
                alert.setContentText("Please select at least one symptom to proceed.");
                alert.showAndWait();
                return;
            }

            // 1. Loading State
            String originalText = analyzeBtn.getText();
            analyzeBtn.setText("⏳ Analyzing...");
            analyzeBtn.setDisable(true);

            // 2. Pause
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));

            pause.setOnFinished(event -> {
                // Ensure UI updates run on the JavaFX thread
                Platform.runLater(() -> {
                    // 3. Restore State
                    analyzeBtn.setText(originalText);
                    analyzeBtn.setDisable(false);

                    try {
                        // 4. Run Analysis
                        Disease d = symptomChecker.analyzeSymptoms(selected);

                        // --- SUCCESS POPUP ---
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.initOwner(window);
                        dialog.setTitle("Analysis Results");
                        dialog.setResizable(true);

                        DialogPane dialogPane = dialog.getDialogPane();
                        dialogPane.getStyleClass().add("results-dialog-pane");
                        dialogPane.setMinWidth(600);
                        dialogPane.setMinHeight(600);
                        dialogPane.setPrefSize(600, 700);

                        if (getClass().getResource("/style.css") != null) {
                            dialogPane.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                        }

                        BorderPane mainContainer = new BorderPane();
                        mainContainer.setPadding(new Insets(0, 0, 20, 0));

                        VBox header = new VBox(5);
                        header.getStyleClass().add("results-header");
                        header.setPadding(new Insets(20));
                        Label lblDiagnosis = new Label(d.getName());
                        lblDiagnosis.getStyleClass().add("results-diagnosis-title");
                        Label lblCategory = new Label(d.getCategory() + " Category");
                        lblCategory.getStyleClass().add("results-category-badge");
                        header.getChildren().addAll(lblDiagnosis, lblCategory);
                        mainContainer.setTop(header);

                        VBox body = new VBox(25);
                        body.setPadding(new Insets(20));

                        VBox firstAidSection = new VBox(10);
                        Label lblFirstAidTitle = new Label("🚑 Immediate Steps / First Aid");
                        lblFirstAidTitle.getStyleClass().add("results-section-title");

                        VBox stepsBox = new VBox(8);
                        if (d.getFirstAidSteps() != null && !d.getFirstAidSteps().isEmpty()) {
                            for(String step : d.getFirstAidSteps()) {
                                HBox stepRow = new HBox(10);
                                Label dot = new Label("•");
                                dot.setStyle("-fx-text-fill: #0052D4; -fx-font-size: 18px;");
                                Label stepLbl = new Label(step);
                                stepLbl.setWrapText(true);
                                stepLbl.getStyleClass().add("results-text");
                                stepRow.getChildren().addAll(dot, stepLbl);
                                stepsBox.getChildren().add(stepRow);
                            }
                        } else {
                            stepsBox.getChildren().add(new Label("No specific first aid steps needed."));
                        }
                        firstAidSection.getChildren().addAll(lblFirstAidTitle, stepsBox);

                        VBox doctorSection = new VBox(15);
                        Label lblDocTitle = new Label("👨‍⚕️ Recommended Specialists Near You");
                        lblDocTitle.getStyleClass().add("results-section-title");

                        FlowPane doctorsGrid = new FlowPane();
                        doctorsGrid.setHgap(20);
                        doctorsGrid.setVgap(20);
                        doctorsGrid.setPrefWrapLength(500);

                        List<Doctor> docs = doctorDAO.getDoctorsByDisease(d.getDiseaseID());
                        if(docs.isEmpty()) {
                            Label noDoc = new Label("No specific specialists found for this condition.");
                            noDoc.getStyleClass().add("results-text");
                            doctorsGrid.getChildren().add(noDoc);
                        } else {
                            for(Doctor doc : docs) {
                                doctorsGrid.getChildren().add(createDoctorProfileCard(doc));
                            }
                        }
                        doctorSection.getChildren().addAll(lblDocTitle, doctorsGrid);

                        body.getChildren().addAll(firstAidSection, new Separator(), doctorSection);

                        ScrollPane scroll = new ScrollPane(body);
                        scroll.setFitToWidth(true);
                        scroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
                        mainContainer.setCenter(scroll);

                        dialogPane.setContent(mainContainer);
                        dialogPane.getButtonTypes().add(ButtonType.OK);

                        dialog.showAndWait();

                    } catch(Exception ex) {
                        // --- WARNING POPUP ---
                        Dialog<ButtonType> warningDialog = new Dialog<>();
                        warningDialog.initOwner(window);
                        warningDialog.setTitle("Analysis Status");

                        VBox box = new VBox(15);
                        box.setAlignment(Pos.CENTER);
                        box.setPadding(new Insets(30, 20, 30, 20));
                        box.setPrefWidth(500);
                        box.setStyle("-fx-background-color: white;");

                        HBox headerRow = new HBox(10);
                        headerRow.setAlignment(Pos.CENTER);
                        Label icon = new Label("⚠️");
                        icon.setStyle("-fx-font-size: 24px; -fx-text-fill: #f39c12;");
                        Label warningTitle = new Label("Analysis Inconclusive");
                        warningTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #333;");
                        headerRow.getChildren().addAll(icon, warningTitle);

                        Label msg = new Label("We could not identify a specific disease with high confidence based on your selected symptoms.");
                        msg.setWrapText(true);
                        msg.setTextAlignment(TextAlignment.CENTER);
                        msg.setMinHeight(Region.USE_PREF_SIZE);
                        msg.setStyle("-fx-font-size: 14px; -fx-text-fill: #666; -fx-padding: 0 0 10 0;");

                        Label advice = new Label("We recommend consulting a doctor for a professional checkup.");
                        advice.setWrapText(true);
                        advice.setTextAlignment(TextAlignment.CENTER);
                        advice.setMinHeight(Region.USE_PREF_SIZE);
                        advice.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #0052D4;");

                        Button okBtn = new Button("Understood");
                        okBtn.getStyleClass().add("button-primary-blue");
                        okBtn.setPrefWidth(150);
                        okBtn.setOnAction(evt -> warningDialog.close());

                        box.getChildren().addAll(headerRow, msg, advice, okBtn);

                        warningDialog.getDialogPane().setContent(box);
                        warningDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
                        Node closeButton = warningDialog.getDialogPane().lookupButton(ButtonType.CLOSE);
                        closeButton.setVisible(false);
                        closeButton.setManaged(false);

                        if (getClass().getResource("/style.css") != null) {
                            warningDialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
                        }
                        warningDialog.showAndWait();
                    }
                });
            });

            pause.play();
        });

        ScrollPane mainScroll = new ScrollPane(mainLayout);
        mainScroll.setFitToWidth(true);
        mainScroll.getStyleClass().add("transparent-scroll");
        switchScene(mainScroll);
    }

    // ==========================================
    // SCREEN 5: DOCTORS DIRECTORY
    // ==========================================
    private void showDoctorsScreen(User user) {
        VBox mainLayout = new VBox(25);
        mainLayout.getStyleClass().add("page-background");
        mainLayout.setPadding(new Insets(40));

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        Button backBtn = new Button("⬅");
        backBtn.getStyleClass().add("back-button-circle");
        backBtn.setOnAction(e -> showDashboardScreen(user));

        VBox titleBox = new VBox(5);
        Label title = new Label("Find a Specialist");
        title.getStyleClass().add("page-title");
        Label subtitle = new Label("Browse our top-rated medical professionals.");
        subtitle.getStyleClass().add("page-subtitle");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        TextField searchField = new TextField();
        searchField.setPromptText("Search by name or specialty...");
        searchField.getStyleClass().add("search-bar");
        searchField.setPrefWidth(300);
        header.getChildren().addAll(backBtn, titleBox, spacer, searchField);

        FlowPane doctorsGrid = new FlowPane();
        doctorsGrid.setHgap(25);
        doctorsGrid.setVgap(25);
        doctorsGrid.setAlignment(Pos.TOP_LEFT);
        doctorsGrid.setPadding(new Insets(10));

        List<Doctor> allDoctors = doctorDAO.getAllDoctors();
        renderDoctorCards(doctorsGrid, allDoctors);

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String lowerVal = newVal.toLowerCase();
            List<Doctor> filtered = new ArrayList<>();
            for(Doctor d : allDoctors) {
                if (d.getName().toLowerCase().contains(lowerVal) || d.getSpecialization().toLowerCase().contains(lowerVal)) {
                    filtered.add(d);
                }
            }
            renderDoctorCards(doctorsGrid, filtered);
        });

        ScrollPane scroll = new ScrollPane(doctorsGrid);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.getStyleClass().add("transparent-scroll");

        mainLayout.getChildren().addAll(header, scroll);
        switchScene(mainLayout);
    }

    // ==========================================
    // HELPERS
    // ==========================================
    // ==========================================
    // HELPER: LEFT BRANDING PANE (Position Fixed)
    // ==========================================
    private VBox createLeftBrandingPane() {
        VBox leftPane = new VBox(15);
        leftPane.getStyleClass().add("left-branding-pane");

        // --- POSITIONING SETTINGS ---

        // 1. Move everything to the BOTTOM LEFT so it doesn't cover faces
        leftPane.setAlignment(Pos.BOTTOM_LEFT);

        // 2. Fine-tune the position with Padding
        // Format: new Insets(TOP, RIGHT, BOTTOM, LEFT)
        // Change '100' to move it higher or lower from the bottom edge.
        // Change '60' to move it left or right.
        leftPane.setPadding(new Insets(0, 40, 150, 80));

        // -----------------------------

        HBox.setHgrow(leftPane, Priority.ALWAYS);

        Label logoIcon = new Label("✚");
        logoIcon.getStyleClass().add("brand-logo-icon");

        Label brandTitle = new Label("DocFinder\nHealth System");
        brandTitle.getStyleClass().add("brand-title-split");
        brandTitle.setWrapText(true);

        Label brandDesc = new Label("Precision diagnostics and expert care,\npowered by advanced identification technology.");
        brandDesc.getStyleClass().add("brand-desc-split");
        brandDesc.setWrapText(true);
        // Limit width so it doesn't stretch too far
        brandDesc.setMaxWidth(400);

        leftPane.getChildren().addAll(logoIcon, brandTitle, brandDesc);
        return leftPane;
    }
    private VBox createActionCard(String iconEmoji, String titleText, String descText) {
        VBox card = new VBox(15);
        card.getStyleClass().add("action-card");
        card.setAlignment(Pos.CENTER);
        card.setMinWidth(300);
        card.setMaxWidth(300);
        card.setMinHeight(250);
        Label icon = new Label(iconEmoji);
        icon.setStyle("-fx-font-size: 50px;");
        Label title = new Label(titleText);
        title.getStyleClass().add("card-title");
        Label desc = new Label(descText);
        desc.getStyleClass().add("card-desc");
        desc.setWrapText(true);
        desc.setTextAlignment(TextAlignment.CENTER);
        card.getChildren().addAll(icon, title, desc);
        return card;
    }

    // ==========================================
    // UPDATE THIS HELPER METHOD
    // ==========================================
    private void renderDoctorCards(FlowPane grid, List<Doctor> doctors) {
        grid.getChildren().clear();
        if(doctors.isEmpty()) {
            Label noData = new Label("No doctors found matching your search.");
            noData.setStyle("-fx-font-size: 16px; -fx-text-fill: #888;");
            grid.getChildren().add(noData);
        } else {
            // "Staggered" Animation Logic
            int delay = 0;
            for (Doctor d : doctors) {
                VBox card = createDoctorProfileCard(d);

                // Initially hide the card
                card.setOpacity(0);
                card.setTranslateY(20); // Move it down slightly

                grid.getChildren().add(card);

                // Create a fade-in + slide-up animation
                javafx.animation.FadeTransition ft = new javafx.animation.FadeTransition(javafx.util.Duration.millis(400), card);
                ft.setToValue(1.0);

                javafx.animation.TranslateTransition tt = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(400), card);
                tt.setToY(0);

                javafx.animation.ParallelTransition pt = new javafx.animation.ParallelTransition(ft, tt);
                pt.setDelay(javafx.util.Duration.millis(delay)); // Wait a bit before starting
                pt.play();

                delay += 100; // Increase delay for the next card (100ms)
            }
        }
    }

    private VBox createDoctorProfileCard(Doctor d) {
        VBox card = new VBox(10);
        card.getStyleClass().add("doctor-profile-card");
        card.setPrefWidth(280);
        card.setAlignment(Pos.TOP_CENTER);
        Label avatar = new Label("👨‍⚕️");
        avatar.setStyle("-fx-font-size: 50px; -fx-background-color: #e3f2fd; -fx-background-radius: 50; -fx-padding: 10px;");
        Label name = new Label(d.getName());
        name.getStyleClass().add("doc-name");
        Label spec = new Label(d.getSpecialization());
        spec.getStyleClass().add("doc-badge");
        Separator sep = new Separator();
        sep.setMaxWidth(200);
        VBox details = new VBox(5);
        details.setAlignment(Pos.CENTER_LEFT);
        details.setPadding(new Insets(0, 15, 0, 15));
        details.getChildren().add(new Label("📞  " + d.getContactNumber()));
        details.getChildren().add(new Label("📍  " + d.getClinicAddress()));
        details.getChildren().add(new Label("⏰  " + d.getClinicHours()));
        details.getChildren().forEach(node -> node.setStyle("-fx-text-fill: #555; -fx-font-size: 13px;"));
        Button bookBtn = new Button("Book Appointment");
        bookBtn.getStyleClass().add("button-small-outline");
        bookBtn.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(bookBtn, new Insets(10, 15, 0, 15));
        card.getChildren().addAll(avatar, name, spec, sep, details, bookBtn);
        return card;
    }

    private TitledPane createCategoryPane(String t) {
        TilePane tp = new TilePane();
        tp.setHgap(20);
        tp.setVgap(15);
        tp.setPrefColumns(2);
        tp.setPadding(new Insets(15));
        return new TitledPane(t, tp);
    }

    private void addToPane(TitledPane tp, CheckBox cb) {
        ((TilePane)tp.getContent()).getChildren().add(cb);
    }
    // ==========================================
    // HELPER: TOAST NOTIFICATION
    // ==========================================
    // ==========================================
    // HELPER: TOAST NOTIFICATION
    // ==========================================
    private void showToast(String message) {
        Label toast = new Label(message);
        toast.getStyleClass().add("toast-bar"); // Ensure this class is in style.css

        StackPane root = (StackPane) window.getScene().getRoot();
        root.getChildren().add(toast);
        StackPane.setAlignment(toast, Pos.BOTTOM_CENTER);
        StackPane.setMargin(toast, new Insets(0, 0, 50, 0));

        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), toast);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        javafx.animation.PauseTransition stay = new javafx.animation.PauseTransition(javafx.util.Duration.seconds(3));

        javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(500), toast);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> root.getChildren().remove(toast));

        javafx.animation.SequentialTransition sq = new javafx.animation.SequentialTransition(fadeIn, stay, fadeOut);
        sq.play();
    }
}