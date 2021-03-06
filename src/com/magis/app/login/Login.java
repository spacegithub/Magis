package com.magis.app.login;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.magis.app.Main;
import com.magis.app.UI.Alert;
import com.magis.app.UI.UIComponents;
import com.magis.app.home.HomePage;
import com.magis.app.internet.InternetConnector;
import com.magis.app.models.StudentModel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicBoolean;


public class Login {
    private static Rectangle rectangle;
    private static ImageView magisLogo;
    private static GridPane loginGridPane;
    private static GridPane passwordVerifierGridPane;
    private static GridPane passwordBox;
    private static JFXTextField userNameTextField;
    private static JFXTextField firstNameTextField;
    private static JFXTextField lastNameTextField;
    private static JFXPasswordField passwordTextField;
    private static Label notSatisfied;
    private static JFXButton signInUp;
    private static JFXButton bottomButton;
    private static boolean signUpVisible;

//    private static JFXCheckBox longEnoughCheck;
//    private static JFXCheckBox containsUpperCaseCheck;
//    private static JFXCheckBox containsLowerCaseCheck;
//    private static JFXCheckBox containsNumberCheck;

    public static void Page() {
        //check for internet connection in background, and it if fails, output a message to the user
        new Thread(() -> Platform.runLater(InternetConnector::checkInternet)).start();

        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);

        StackPane boxBackground = new StackPane();
        boxBackground.getStyleClass().addAll("drop-shadow");

        rectangle = new Rectangle();
        rectangle.setHeight(450);
        rectangle.setWidth(450);
        rectangle.setFill(Color.valueOf("#ddd")); //#ededed

        magisLogo = new ImageView("com/magis/app/resources/magis-color-small.png");
        magisLogo.getStyleClass().addAll("drop-shadow");

        loginGridPane = new GridPane();
        loginGridPane.setAlignment(Pos.CENTER);
        loginGridPane.setHgap(10);
        loginGridPane.setVgap(25);
        loginGridPane.setPadding(new Insets(25,25,25,25));

        passwordVerifierGridPane = UIComponents.createPasswordVerifierHelper();
        passwordVerifierGridPane.setVisible(false);
        notSatisfied = (Label) passwordVerifierGridPane.getChildren().get(8);
//        passwordVerifierGridPane = new GridPane();
//        passwordVerifierGridPane.setVisible(false);
//        passwordVerifierGridPane.setAlignment(Pos.CENTER);
//        passwordVerifierGridPane.setHgap(10);
//        passwordVerifierGridPane.setVgap(25);


//        longEnoughCheck = passwordAssistantCheckBox();
//        Label longEnoughLabel = passwordAssistantLabel("Greater than 8 characters", longEnoughCheck);
//        containsUpperCaseCheck = passwordAssistantCheckBox();
//        Label containsUpperCaseLabel = passwordAssistantLabel("Contains at least one uppercase letter", containsUpperCaseCheck);
//        containsLowerCaseCheck = passwordAssistantCheckBox();
//        Label containsLowerCaseLabel = passwordAssistantLabel("Contains at least one lowercase letter", containsLowerCaseCheck);
//        containsNumberCheck = passwordAssistantCheckBox();
//        Label containsNumberLabel = passwordAssistantLabel("Contains at least one number", containsNumberCheck);

//        notSatisfied = new Label("Please meet these standards!");
//        notSatisfied.setStyle("-fx-text-fill: #FF1744"); //Red A400
//        notSatisfied.setVisible(false);

//        passwordVerifierGridPane.add(longEnoughCheck,0,1);
//        passwordVerifierGridPane.add(longEnoughLabel,1,1);
//        passwordVerifierGridPane.add(containsUpperCaseCheck,0,2);
//        passwordVerifierGridPane.add(containsUpperCaseLabel,1,2);
//        passwordVerifierGridPane.add(containsLowerCaseCheck,0,3);
//        passwordVerifierGridPane.add(containsLowerCaseLabel,1,3);
//        passwordVerifierGridPane.add(containsNumberCheck,0,4);
//        passwordVerifierGridPane.add(containsNumberLabel,1,4);
//        passwordVerifierGridPane.add(notSatisfied,1,5);

        /*
        Sign-in area
         */
        userNameTextField = createJFXTextField("Username");
        passwordTextField = UIComponents.createJFXPasswordField("Password");

        passwordTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) attemptSignIn(userNameTextField.getText(), passwordTextField.getText());
        });

        /*
        Sign-up area
         */
        firstNameTextField = createJFXTextField("First name");
        lastNameTextField = createJFXTextField("Last name");

        loginGridPane.add(userNameTextField, 0, 1);
        loginGridPane.add(passwordTextField,0,2);

        signInUp = new JFXButton("Sign In");
        signInUp.getStyleClass().addAll("jfx-button-raised", "jfx-button-raised-color");
        signInUp.setOnMouseClicked(e -> attemptSignIn(userNameTextField.getText(), passwordTextField.getText()));

        bottomButton = new JFXButton("Create Account");
        bottomButton.getStyleClass().addAll("jfx-button-flat", "jfx-button-flat-color");
        bottomButton.setOnMouseClicked(e -> showSignUp());

        /*
        Create a "box" to show where the requirements go to for better UX
                          3
                       ________________________
        username      | password requirement #1
        fname        2| password requirement #2
        lname   1_____| password requirement #3
        password_______ password requirement #4
                  4  5|_______________________
                                6
         */
        passwordBox = new GridPane();
        passwordBox.setVisible(false);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setHgap(0);
        passwordBox.setVgap(0);
        passwordBox.add(UIComponents.createRectangle(30,1),1,3); //1
        passwordBox.add(UIComponents.createRectangle(1,100),2,2); //2
        passwordBox.add(UIComponents.createRectangle(250,1),2,1); //3
        passwordBox.add(UIComponents.createRectangle(0,49),0,4); //space
        passwordBox.add(UIComponents.createRectangle(30,1),1,5); //4
        passwordBox.add(UIComponents.createRectangle(1,15),2,6); //5
        passwordBox.add(UIComponents.createRectangle(250,1),2,7); //6


        boxBackground.getChildren().addAll(rectangle, magisLogo, passwordBox, passwordVerifierGridPane, loginGridPane, signInUp, bottomButton);

        StackPane.setAlignment(magisLogo, Pos.TOP_CENTER);
        StackPane.setMargin(magisLogo, new Insets(50,0,0,0));

        StackPane.setAlignment(loginGridPane, Pos.CENTER);
        StackPane.setMargin(loginGridPane, new Insets(0,0,50,0));

        StackPane.setAlignment(passwordBox, Pos.CENTER);
        StackPane.setMargin(passwordBox, new Insets(0,0,18,415));

        StackPane.setAlignment(passwordVerifierGridPane, Pos.CENTER);
        StackPane.setMargin(passwordVerifierGridPane, new Insets(38,0,50,475));

        StackPane.setAlignment(signInUp, Pos.CENTER);
        StackPane.setMargin(signInUp, new Insets(175,0,0,0));

        StackPane.setAlignment(bottomButton, Pos.BOTTOM_CENTER);
        StackPane.setMargin(bottomButton, new Insets(0,0,20,0));

        content.getChildren().add(boxBackground);

        Main.setScene(content, "Magis");
    }

//    /**
//     * Create a Label that is used in the password verifier assistant
//     * @param requirement the text that presents one of the requirements for the password
//     * @param checkBox the checkbox associated with the label. The state of the checkbox controls the color of the label
//     * @return a label
//     */
//    private static Label passwordAssistantLabel(String requirement, JFXCheckBox checkBox) {
//        Label label = new Label(requirement);
//        label.setStyle("-fx-text-fill: #FF1744"); //Red A400 (default)
//        label.setAlignment(Pos.CENTER_RIGHT);
//        checkBox.selectedProperty().addListener((observable, oldVal, newVal) -> {
//            if (newVal) label.setStyle("-fx-text-fill: #00C853"); //Green A700
//            else label.setStyle("-fx-text-fill: #FF1744"); //Red A400
//        });
//        return label;
//    }



//    /**
//     * create a JFXPasswordField that is used in the sign-in and sign-up gridpane
//     * @param promptText the text inside the text field to indicate to the user the purpose of the text field
//     * @return a JFXPasswordField
//     */
//    private static JFXPasswordField createJFXPasswordField(String promptText) {
//        JFXPasswordField passwordField = new JFXPasswordField();
//        passwordField.setPromptText(promptText);
//        passwordField.getStyleClass().add("sign-in-field");
//        return passwordField;
//    }

    /**
     * Create a JFXTextField that is used in the sign-in and sign-up grid pane
     * @param promptText the text inside the text field to indicate to the user the purpose of the text field
     * @return a JFXTextField
     */
    private static JFXTextField createJFXTextField(String promptText) {
        JFXTextField textField = new JFXTextField();
        textField.setPromptText(promptText);
        textField.getStyleClass().addAll("jfx-sign-in-field", "sign-in-field");
        return textField;
    }

    /**
     * Apply the color settings associated with the student's account
     * @param student the student
     */
    private static void applyColorSettings(StudentModel.Student student) {
        //Remove the default light css
        Main.scene.getStylesheets().remove("com/magis/app/css/lightmode.css");
        //apply the student's light or dark css
        String lightOrDark = student.getDarkMode() ? "com/magis/app/css/darkmode.css" : "com/magis/app/css/lightmode.css";
        Main.scene.getStylesheets().add(lightOrDark);

        //Remove the default pink color
        Main.scene.getStylesheets().remove("com/magis/app/css/pink.css");
        //apply the student's selected color
        Main.scene.getStylesheets().add("com/magis/app/css/" + student.getTheme() + ".css");

//        UIComponents.applyShadowColor();
    }

    /**
     * Attempt to sign in an existing user
     * @param username the username
     * @param password the password
     */
    private static void attemptSignIn(String username, String password) {
        Main.studentModel.initializeStudent(username.toLowerCase());
        StudentModel.Student student = Main.studentModel.getStudent();
        if (username.length() == 0) { //blank username
            Alert.showAlert("Error", "Please enter a username.");
        } else if (student != null) { //if that student exists
            if (password.length() == 0) { //blank password
              Alert.showAlert("Error", "Please enter a password");
            } else if (Password.passwordMatches(student, password)) { //success
                Main.username = username;
                Main.useAnimations = student.useAnimations();
                Main.isLoggedIn = true;
                applyColorSettings(student);
                HomePage.getInstance().Page();
            } else { //incorrect password
                Alert.showAlert("Incorrect Password", "Password does not match. Please try again.");
            }
        } else { //no username
            Alert.showAlert("Error", "Username not found. Please try again.");
        }
    }

    /**
     * Attempt to sign up a new user
     * @param username the username of user
     * @param firstName the first name
     * @param lastName the last name
     * @param password the password
     */
    private static void attemptSignUp(String username, String firstName, String lastName, String password) {
        if (username.length() == 0 || firstName.length() == 0 || lastName.length() == 0) {
            Alert.showAlert("Error", "Please fill in all of the fields.");
            return;
        }
        //capitalize the first letter of the name
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();

        String salt = Password.generateSalt(); //generateRecentActivity salt for password
        String passwordHash = Password.hash(password, salt); //hash the password
        int result = Main.studentModel.addStudent(username, firstName, lastName, passwordHash, salt); //try to add the new student

        if (result == -1) {
            Alert.showAlert("Error", "Username already exists. Please try another username.");
        } else { //success
            Main.studentModel.initializeStudent(username);
            Main.username = username;
            Main.isLoggedIn = true;
            HomePage.getInstance().Page();
        }
    }

    /**
     * Change the view of the scene to show the sign-up window instead of the sign-in window.
     */
    private static void showSignUp() {
        AtomicBoolean validPassword = new AtomicBoolean(false); //keep track of the password is valid
        signUpVisible = true; //used to determine if we should show the password verifier assistant
        grow(); //grow the size size of the box

        //Rearrange items in the grid to make way for "first name" and "last name"
        loginGridPane.getChildren().remove(passwordTextField);
        loginGridPane.add(firstNameTextField,0,2);
        loginGridPane.add(lastNameTextField,0,3);
        loginGridPane.add(passwordTextField,0,4);

        /*
        If the password field already has text in it, show the password helper anyway
        Else the user might be confused as to why they can't click "sign in" when all the fields are already filled
         */
        if (passwordTextField.getText().length() > 0) {
            validPassword.set(Password.checkRequirements(passwordTextField.getText(), passwordVerifierGridPane));
//            if (Password.longEnough(passwordTextField.getText())) longEnoughCheck.setSelected(true);
//            else longEnoughCheck.setSelected(false);
//            if (Password.containsUpperCase(passwordTextField.getText())) containsUpperCaseCheck.setSelected(true);
//            else containsUpperCaseCheck.setSelected(false);
//            if (Password.containsLowerCase(passwordTextField.getText())) containsLowerCaseCheck.setSelected(true);
//            else containsLowerCaseCheck.setSelected(false);
//            if (Password.containsDigit(passwordTextField.getText())) containsNumberCheck.setSelected(true);
//            else containsNumberCheck.setSelected(false);
//            validPassword.set(longEnoughCheck.isSelected() && containsUpperCaseCheck.isSelected() && containsLowerCaseCheck.isSelected() && containsNumberCheck.isSelected());

            passwordVerifierGridPane.setVisible(true);
            passwordBox.setVisible(true);
        }

        //If password field is focused, show the password verifier helper (if it wasn't already visible)
        passwordTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue && signUpVisible) {
                passwordBox.setVisible(true);
                passwordVerifierGridPane.setVisible(true);
            }
//            else passwordVerifierGridPane.setVisible(false);
        });

        //try to log in if the user presses enter while focussed on the password box
        passwordTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER && validPassword.get()) {
                attemptSignUp(userNameTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), passwordTextField.getText());
            } else if (e.getCode() == KeyCode.ENTER && !validPassword.get()) {
                notSatisfied.setVisible(true);
            }
        });

        //for every key typed, check if the password is valid
        passwordTextField.setOnKeyReleased(e -> {
//            if (Password.longEnough(passwordTextField.getText())) longEnoughCheck.setSelected(true);
//            else longEnoughCheck.setSelected(false);
//            if (Password.containsUpperCase(passwordTextField.getText())) containsUpperCaseCheck.setSelected(true);
//            else containsUpperCaseCheck.setSelected(false);
//            if (Password.containsLowerCase(passwordTextField.getText())) containsLowerCaseCheck.setSelected(true);
//            else containsLowerCaseCheck.setSelected(false);
//            if (Password.containsDigit(passwordTextField.getText())) containsNumberCheck.setSelected(true);
//            else containsNumberCheck.setSelected(false);
            validPassword.set(Password.checkRequirements(passwordTextField.getText(), passwordVerifierGridPane));
            if (validPassword.get()) {
                notSatisfied.setVisible(false);
            }
        });
        signInUp.setText("Sign up"); //change text of the button

        //change functionality of the button
        signInUp.setOnMouseClicked(e -> {
            if (validPassword.get()) {
                attemptSignUp(userNameTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), passwordTextField.getText());
            } else {
                notSatisfied.setVisible(true);
            }
        });

        //change the text of the button
        bottomButton.setText("Back");
        //change the functionality of the button
        bottomButton.setOnMouseClicked(e -> {
            passwordVerifierGridPane.setVisible(false);
            passwordBox.setVisible(false);
            showSignIn();
        });
    }

    /**
     * Change the view of the scene to show the sign-in window instead of the sign-up window.
     */
    private static void showSignIn() {
        signUpVisible = false; //used to determine if we should show the password verifier assistant
        shrink(); //shrink the size of the box

        //remove the "first name" and "lastname" fields
        loginGridPane.getChildren().remove(firstNameTextField);
        loginGridPane.getChildren().remove(lastNameTextField);
        loginGridPane.getChildren().remove(passwordTextField);
        loginGridPane.add(passwordTextField,0,2);

        //try to log in
        passwordTextField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) attemptSignIn(userNameTextField.getText(), passwordTextField.getText());
        });
        //remove checking if the password is valid
        passwordTextField.setOnKeyReleased(null);
        //change the text of the button
        signInUp.setText("Sign in");
        //change the functionality of the button
        signInUp.setOnMouseClicked(e -> attemptSignIn(userNameTextField.getText(), passwordTextField.getText()));
        //change the text of the button
        bottomButton.setText("Create Account");
        //change the functionality of the button
        bottomButton.setOnMouseClicked(e -> showSignUp());
    }

    private static void grow() {
        UIComponents.translate(magisLogo, 0.3,0,0,0,-50);
        UIComponents.scale(rectangle,0.3,1.8,1.222);
        UIComponents.translate(loginGridPane,0.3,0,0,0,-18);
        UIComponents.translate(signInUp,0.3,0,0,0,50);
        UIComponents.translate(bottomButton,0.3,0,0,0,50);
    }

    private static void shrink() {
        UIComponents.translate(magisLogo, 0.3,0,0,-50,0);
        UIComponents.scale(rectangle,0.3,1,1);
        UIComponents.translate(loginGridPane,0.3,0,0,-18,0);
        UIComponents.translate(signInUp,0.3,0,0,50,0);
        UIComponents.translate(bottomButton,0.3,0,0,50,0);
    }
}
