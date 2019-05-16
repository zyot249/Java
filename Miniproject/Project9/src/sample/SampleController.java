package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("layout/menu_window.fxml"));
                Stage stage = new Stage();
                stage.setTitle("First Menu");
                stage.setScene(new Scene(root, 300, 275));
                stage.show();

                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


}
