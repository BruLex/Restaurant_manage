package app;

import app.Controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.InputStream;

public class Main extends Application {

    private final double MINIMUM_WINDOW_WIDTH = 800.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;
    private Stage stage;

    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
        stage.setTitle("Laba2");
        Controller main = (Controller) replaceSceneContent("main.fxml");
        main.setApp(stage.getScene());
        primaryStage.show();
    }

    private Node replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        AnchorPane page;
        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        page = loader.load(in);
        in.close();
        Scene scene = new Scene(page);
        if (!Double.isNaN(stageWidth))
            stageWidth -= (stage.getWidth() - stage.getScene().getWidth());
        if (!Double.isNaN(stageHeight))
            stageHeight -= (stage.getHeight() - stage.getScene().getHeight());
        if (!Double.isNaN(stageWidth))
            page.setPrefWidth(stageWidth);
        if (!Double.isNaN(stageHeight))
            page.setPrefHeight(stageHeight);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Node) loader.getController();
    }
}
