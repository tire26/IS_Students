package com.example.is_students.mai;

import com.example.is_students.mai.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class FXMLPage {

    public static void load(String path, int width, int height, Stage currentStage, User user) {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(path));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), width, height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        currentStage.close();
        Stage stage = new Stage();
        stage.setUserData(user);
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setTitle("ИС Студенты");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
