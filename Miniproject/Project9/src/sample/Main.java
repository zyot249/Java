package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;


public class Main extends Application {
    public static Scene firstScene;
    public static Scene menuScene;
    public static MediaPlayer bgMediaPlayer;
    private Stage currentStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("layout/sample.fxml"));
        firstScene = new Scene(root,1024,600);
        root = FXMLLoader.load(getClass().getResource("layout/menu_window.fxml"));
        root.setId("pane");
        menuScene = new Scene(root,1024,600);
        menuScene.getStylesheets().addAll(getClass().getResource("style/first_scene_style.css").toExternalForm());
        currentStage = primaryStage;

        // background song
        URL songPath = getClass().getResource("sound/fortunate_son_various_artists.mp3");
        Media sound = new Media(songPath.toString());
        bgMediaPlayer = new MediaPlayer(sound);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(firstScene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
