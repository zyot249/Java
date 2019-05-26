package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SampleController implements Initializable{

    @FXML
    private Button btnFirst;

    @FXML
    private TextField tfShowMessage;

    @FXML
    private Button btnClose;

    @FXML
    private Button btnOpenMenu;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnFirst.setOnAction(actionEvent -> {
            System.out.println("Button Clicked");
            Date now = new Date();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

            String dateTimeString = df.format(now);
            tfShowMessage.setText(dateTimeString);
        });

        btnClose.setOnAction(actionEvent -> {
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        btnOpenMenu.setOnAction(actionEvent -> {
                Stage stage = (Stage) ((Node) (actionEvent.getSource())).getScene().getWindow();
                stage.setTitle("First Menu");
                stage.setScene(Main.menuScene);
                Main.bgMediaPlayer.setOnEndOfMedia(() -> Main.bgMediaPlayer.seek(Duration.ZERO));
                Main.bgMediaPlayer.setMute(false);
                Main.bgMediaPlayer.play();
        });
    }


}
