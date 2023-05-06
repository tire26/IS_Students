package com.example.is_students.mai;

import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) {

        FXMLPage.load("/pages/mainPage.fxml", 600, 300, stage, null);
    }

    public static void main(String[] args) {
        launch();
    }

}