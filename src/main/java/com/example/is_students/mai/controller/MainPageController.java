package com.example.is_students.mai.controller;


import com.example.is_students.mai.FXMLPage;
import com.example.is_students.mai.entity.User;
import com.example.is_students.mai.service.MainPageService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPageController {

    MainPageService mainPageService = new MainPageService();

    @FXML
    private Button enter;

    @FXML
    private TextField login;

    @FXML
    private Text message;

    @FXML
    private PasswordField password;

    @FXML
    public void enterOnAction() {
        enter.setDisable(true);
        String login = this.login.getText();
        String password = this.password.getText();
        User user;

        user = mainPageService.findUser(login, password);
        if (user != null) {
            Stage stage = (Stage) (enter.getScene().getWindow());
            switch (user.getUserTypeId().intValue()) {
                case 1 -> FXMLPage.load("/pages/usersPage/studentPage.fxml", 846, 346, stage, user);
                case 2 -> FXMLPage.load("/pages/usersPage/teacherPage.fxml", 846, 346, stage, user);
                case 4 -> FXMLPage.load("/pages/usersPage/administratorPage.fxml", 924, 400, stage, user);
                default -> message.setText("Тип пользователя неопределённ, сообщите администратору");
            }

        } else {
            message.setText("Введён неправильный логин или пароль");
            enter.setDisable(false);
        }
    }
}
