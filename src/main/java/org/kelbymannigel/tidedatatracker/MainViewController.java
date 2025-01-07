package org.kelbymannigel.tidedatatracker;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainViewController {

    /** Contains the days to be displayed. */
    private final ObservableList<Day> days = FXCollections.observableArrayList();

    // CONTROLS

    @FXML
    private DatePicker startDatePicker,
                       endDatePicker;

    @FXML
    private TextField startTimeTextField,
                      endTimeTextField,
                      minHeightTextField,
                      maxHeightTextField,
                      minMoonIlluminationTextField,
                      maxMoonIlluminationTextField;

    @FXML
    private RadioButton lowTideRadioButton,
                        highTideRadioButton;

    @FXML
    private Button searchButton,
                   loadDataButton,
                   viewAllButton;

    // LAYOUTS

    @FXML
    private VBox daysVBox;

    // ACTIONS

    @FXML
    private void initialize() {
        // listener for refreshing the daysVBox when a change is made
        days.addListener((Observable observable) -> refreshDaysVBox());
    }

    private void refreshDaysVBox() {
        // clear the vBox
        daysVBox.getChildren().clear();
        // add the day GUIs from the ObservableList to the vBox
        for(Day day : days) {
            daysVBox.getChildren().add(day.getGUI());
        }
    }

    @FXML
    private void onLoadDataButtonPressed(ActionEvent event) {
        try {
            // load LoadDataView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadDataView.fxml"));
            // load the root node
            Parent root = loader.load();
            // get the stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // create a new scene with LoadDataView
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onSearchButtonPressed() {
    }

    @FXML
    private void onViewAllButtonPressed() {
        DayManager manager = DayManager.getInstance();
        // add all days to the ObservableList
        if(manager.getYearData() != null) {
            days.clear();
            days.addAll(manager.getYearData());
        } else {
            System.out.println("Error: No data available.");
        }
    }
}
