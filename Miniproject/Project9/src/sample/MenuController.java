package sample;

import battleship.BattleshipMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuController implements Initializable, EventHandler<ActionEvent> {
    @FXML
    private Button btnResume;

    @FXML
    private Button btnNewGame;

    @FXML
    private Button btnHighScore;

    @FXML
    private Button btnQuit;

    @FXML
    private Button btnSetting;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnNewGame.setOnAction(this);
        btnResume.setOnAction(this);
        btnHighScore.setOnAction(this);
        btnSetting.setOnAction(this);
        btnQuit.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        Object source = event.getSource();
        Node s = (Node) source;
        Stage stage = (Stage) s.getScene().getWindow();
        if (btnResume.equals(source)) {
            if (BattleshipMain.gameScene != null) {
                stage.setScene(BattleshipMain.gameScene);
                BattleshipMain.menuPlayer.stop();
                BattleshipMain.gamePlayer.play();
            }
        } else if (btnNewGame.equals(source)) {
            BattleshipMain.gameScene = new Scene(new BattleshipMain().createContent());
            stage.setScene(BattleshipMain.gameScene);
            BattleshipMain.menuPlayer.stop();
            BattleshipMain.gamePlayer.play();
        } else if (btnHighScore.equals(source)) {
            btnResume.setVisible(true);
        } else if (btnSetting.equals(source)) {
            if (BattleshipMain.isMute){
                btnSetting.setText("Music Off");
                BattleshipMain.setMute(false);
            }else {
                btnSetting.setText("Music On");
                BattleshipMain.setMute(true);
            }
        } else if (btnQuit.equals(source)) {
            close(stage);
        }
    }

    private static boolean exitCheck(Stage stage) {
        Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to quit?", ButtonType.NO, ButtonType.YES);
        alert.setHeaderText("");
        alert.setTitle("Warning?");
        alert.initOwner(stage);
        Optional<ButtonType> type = alert.showAndWait();
        return type.isPresent() && type.get() == ButtonType.YES;
    }

    public static boolean close(Stage stage) {
        if (exitCheck(stage)) {
            Platform.exit();
            return true;
        }
        return false;
    }

    public void setVisibleResume(boolean visible){
        this.btnResume.setVisible(visible);
    }
}
