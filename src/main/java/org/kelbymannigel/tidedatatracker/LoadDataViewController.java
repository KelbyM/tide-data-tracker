package org.kelbymannigel.tidedatatracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoadDataViewController {

    // CONTROL ELEMENTS

    @FXML
    private Button returnButton,
                   loadDataButton;

    @FXML
    private TextField tideDataFilePathTextField,
                      moonDataFilePathTextField;

    // ACTIONS

    @FXML
    private void onReturnButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onLoadDataButtonPressed() {
        DayManager manager = DayManager.getInstance();
        manager.setTideDataFilePath(tideDataFilePathTextField.getText());
        manager.setMoonDataFilePath(moonDataFilePathTextField.getText());
        manager.parseYear();
    }
}
