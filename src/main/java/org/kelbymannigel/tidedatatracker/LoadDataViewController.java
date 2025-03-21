package org.kelbymannigel.tidedatatracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * The controller class for LoadDataView.fxml.
 */
public class LoadDataViewController {

    // CONTROL

    // BUTTON
    @FXML
    private Button returnButton,
                   loadDataButton,
                   loadDataFromDatabaseButton,
                   saveDataButton,
                   deleteAllDataButton;

    // TEXT FIELD
    @FXML
    private TextField tideDataFilePathTextField,
                      moonDataFilePathTextField;

    // LABEL
    @FXML
    private Label checkFilePathsLabel,
                  saveToDatabaseLabel;

    // ACTION

    /**
     * Handles the action of the "Return" button being pressed in the UI.
     */
    @FXML
    private void onReturnButtonPressed(ActionEvent event) {
        try {
            // load MainView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
            // load the root node
            Parent root = loader.load();
            // get the stage
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            // create a new scene with MainView
            stage.setScene(new Scene(root));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action of the "Load Data" button being pressed in the UI.
     */
    @FXML
    private void onLoadDataButtonPressed() {
        // get the DayManager
        DayManager manager = DayManager.getInstance();
        manager.setTideDataFilePath(tideDataFilePathTextField.getText());
        manager.setMoonDataFilePath(moonDataFilePathTextField.getText());
        // check if the files can be found
        if(!manager.checkFilePaths()) {
            checkFilePathsLabel.setText("Error: Data files not found.");
        }
        else {
            checkFilePathsLabel.setText("The data files have been found.");
            manager.parseYear();
        }
    }

    /**
     * Handles the action of the "Load Data" button being pressed in the UI.
     */
    @FXML
    private void onLoadDataFromDatabaseButtonPressed() {
        // get the DayManager
        DayManager dayManager = DayManager.getInstance();
        dayManager.getYearFromDatabase();
    }

    /**
     * Handles the action of the "Save Data" button being pressed in the UI.
     */
    @FXML
    private void onSaveDataButtonPressed() {
        // get the DayManager
        DayManager dayManager = DayManager.getInstance();
        // write the days to the database
        dayManager.saveYear();
    }

    @FXML
    private void onDeleteAllDataButtonPressed() {
        // delete all data in the database
        DatabaseManager.deleteAllData();
    }
}
