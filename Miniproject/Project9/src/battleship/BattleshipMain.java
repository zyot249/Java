package battleship;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import battleship.Board.Cell;
import javafx.util.Duration;
import sample.MenuController;

import static sample.MenuController.close;

public class BattleshipMain extends Application {

    // scene
    public static Scene menuScene;
    public static Scene gameScene;

    // media player
    public static boolean isMute;
    public static MediaPlayer menuPlayer;
    public static MediaPlayer gamePlayer;
    public static MediaPlayer winPlayer;
    public static MediaPlayer losePlayer;
    public static MediaPlayer bombMissPlayer;
    public static MediaPlayer bombExplorePlayer;

    public static Stage stage;

    private boolean running = false;
    private Board enemyBoard, playerBoard;

    private int shipsToPlace = 5;

    private boolean enemyTurn = false;

    private Random random = new Random();

    public Parent createContent() {
        enemyTurn = false;
        BorderPane root = new BorderPane();
        root.setPrefSize(1024, 600);

        root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Cell cell = (Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {
                System.out.println("YOU WIN");
                winPlayer.seek(Duration.ZERO);
                winPlayer.play();
                openNextGame(stage, true);
            }

            if (enemyTurn)
                enemyMove();
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Cell cell = (Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {
                if (--shipsToPlace == 0) {
                    startGame();
                }
            }
        });


        HBox hbox = new HBox(50, playerBoard, enemyBoard);
        hbox.setAlignment(Pos.CENTER);

        root.setCenter(hbox);

        // add button back
        Button btnBack = new Button();
        btnBack.setText("Back to Menu");
        btnBack.setPadding(new Insets(10));
        root.setRight(btnBack);
        BorderPane.setMargin(btnBack, new Insets(10));

        btnBack.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/layout/menu_window.fxml"));
            try {
                Parent parent = loader.load();
                parent.setId("pane");
                MenuController menuController = loader.getController();
                menuController.setVisibleResume(true);
                menuScene = new Scene(parent, 1024, 600);
                menuScene.getStylesheets().addAll(getClass().getResource("../sample/style/first_scene_style.css").toExternalForm());
                stage.setScene(menuScene);
                BattleshipMain.menuPlayer.play();
                BattleshipMain.gamePlayer.pause();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // add label
        Text text = new Text("YOU                                                                                ENEMY");
        root.setTop(text);
        BorderPane.setMargin(text, new Insets(120, 0, 0, 250));


        return root;
    }

    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) {
                System.out.println("YOU LOSE");
                losePlayer.seek(Duration.ZERO);
                losePlayer.play();
                openNextGame(stage, false);
            }
        }
    }

    private void startGame() {
        // place enemy ships
        int type = 5;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.5), x, y)) {
                type--;
            }
        }

        running = true;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        initScene();
        setBackgroundSong();

        primaryStage.setTitle("Battleship");
        primaryStage.setScene(menuScene);
        primaryStage.setOnCloseRequest(event -> {
            if (!close(stage)) {
                event.consume();
            }
        });
        primaryStage.show();
    }

    private void setBackgroundSong() {
        URL songPath = getClass().getResource("../sample/sound/fortunate_son_various_artists.mp3");
        Media sound = new Media(songPath.toString());
        menuPlayer = new MediaPlayer(sound);
        menuPlayer.setOnEndOfMedia(() -> menuPlayer.seek(Duration.ZERO));

        URL songPath1 = getClass().getResource("../sample/sound/the_strongest.mp3");
        Media sound1 = new Media(songPath1.toString());
        gamePlayer = new MediaPlayer(sound1);
        gamePlayer.setVolume(0.2);
        gamePlayer.setOnEndOfMedia(() -> gamePlayer.seek(Duration.ZERO));

        songPath = getClass().getResource("../sample/sound/winning.mp3");
        sound = new Media(songPath.toString());
        winPlayer = new MediaPlayer(sound);

        songPath = getClass().getResource("../sample/sound/lose.mp3");
        sound = new Media(songPath.toString());
        losePlayer = new MediaPlayer(sound);

        songPath = getClass().getResource("../sample/sound/explosion.mp3");
        sound = new Media(songPath.toString());
        bombExplorePlayer = new MediaPlayer(sound);

        songPath = getClass().getResource("../sample/sound/water_splashing.mp3");
        sound = new Media(songPath.toString());
        bombMissPlayer = new MediaPlayer(sound);

        isMute = true;
        menuPlayer.play();
    }

    public static void setMute(boolean mute) {
        isMute = mute;
        menuPlayer.setMute(mute);
        gamePlayer.setMute(mute);
        winPlayer.setMute(mute);
        losePlayer.setMute(mute);
        bombMissPlayer.setMute(mute);
        bombExplorePlayer.setMute(mute);
    }

    private void initScene() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../sample/layout/menu_window.fxml"));
        root.setId("pane");
        menuScene = new Scene(root, 1024, 600);
        menuScene.getStylesheets().addAll(getClass().getResource("../sample/style/first_scene_style.css").toExternalForm());
        gameScene = new Scene(createContent(), 1024, 600);
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean notifyAfterGame(Stage stage, boolean win) {
        String message = "";
        message += (win) ? "Victory!" : "You're defeated!";
        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION, "Do you want to play a new game?", ButtonType.NO, ButtonType.YES);
        alert.setHeaderText("");
        alert.setTitle(message);
        alert.initOwner(stage);
        Optional<ButtonType> type = alert.showAndWait();
        return type.isPresent() && type.get() == ButtonType.YES;
    }

    public void openNextGame(Stage stage, boolean win) {
        if (notifyAfterGame(stage, win)) {
            gameScene = new Scene(new BattleshipMain().createContent());
            stage.setScene(BattleshipMain.gameScene);
            menuPlayer.stop();
            gamePlayer.seek(Duration.ZERO);
            gamePlayer.play();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/layout/menu_window.fxml"));
            try {
                Parent parent = loader.load();
                parent.setId("pane");
                MenuController menuController = loader.getController();
                menuController.setVisibleResume(false);
                menuScene = new Scene(parent, 1024, 600);
                menuScene.getStylesheets().addAll(getClass().getResource("../sample/style/first_scene_style.css").toExternalForm());
                stage.setScene(menuScene);
                menuPlayer.play();
                gamePlayer.pause();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
