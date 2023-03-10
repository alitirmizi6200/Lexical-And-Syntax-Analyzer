package com.example.LexeicalAndSyntax;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        Parent root = FXMLLoader.load(Main.class.getResource("FXMLMain.fxml"));
        Scene scene = new Scene(root, 1060,800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Lexical Analyzer");
        primaryStage.show();
    }
}
