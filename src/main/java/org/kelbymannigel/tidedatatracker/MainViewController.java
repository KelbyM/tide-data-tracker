package org.kelbymannigel.tidedatatracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainViewController {

    private DayManager manager;

    // CONTROL ELEMENTS

    @FXML
    protected DatePicker startDatePicker,
                         endDatePicker;

    @FXML
    protected TextField startTimeTextField,
                        endTimeTextField,
                        minHeightTextField,
                        maxHeightTextField,
                        minMoonIlluminationTextField,
                        maxMoonIlluminationTextField;

    @FXML
    protected RadioButton lowTideRadioButton,
                          highTideRadioButton;

    @FXML
    protected Button searchButton,
                     loadDataButton;

    // ACTIONS

    @FXML
    protected void onLoadDataButtonPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadDataView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onSearchButtonPressed() {

    }
}
