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

import java.time.LocalTime;
import java.util.ArrayList;

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
        DayManager manager = DayManager.getInstance();
        //Make the locations to store the information
        ArrayList<Day> yearData = manager.getYearData();
        ArrayList<Day> searchResults = new ArrayList<>();

        // get the start and end dates from the date pickers
        int startDay, endDay;
        // set the start date
        if(startDatePicker.getValue() != null) {
            startDay = startDatePicker.getValue().getDayOfYear();
        } else {
            // set to minimum day
            startDay = 1;
        }
        // set the end date
        if(endDatePicker.getValue() != null) {
            endDay = endDatePicker.getValue().getDayOfYear();
        } else {
            // set to maximum day
            endDay = yearData.size();
        }
        // set the start/end time
        LocalTime startTime, endTime;
        if(!(startTimeTextField.getText().trim().equals(""))) {
            String[] parts = startTimeTextField.getText().split(":");
            if(parts.length == 2) {
                startTime = LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else {
                startTime = LocalTime.of(Integer.parseInt(parts[0]), 0);
            }
        } else {
            startTime = LocalTime.of(0, 0);
        }
        if(!(endTimeTextField.getText().trim().equals(""))) {
            String[] parts = endTimeTextField.getText().split(":");
            if(parts.length == 2) {
                endTime = LocalTime.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
            } else {
                endTime = LocalTime.of(Integer.parseInt(parts[0]), 0);
            }
        } else {
            endTime = LocalTime.of(23, 59);
        }

        // get the low or high tide from the radio buttons
        boolean high = highTideRadioButton.isSelected(), low = lowTideRadioButton.isSelected();
        // checking if both are not selected
        if(!(high || low)) {
            high = true;
            low = true;
        }

        // get the tide height from the text fields
        double minHeight, maxHeight;
        if(!(minHeightTextField.getText().trim().equals(""))) {
            minHeight = Double.parseDouble(minHeightTextField.getText());
        } else {
            minHeight = -100;
        }
        if(!(maxHeightTextField.getText().trim().equals(""))) {
            maxHeight = Double.parseDouble(maxHeightTextField.getText());
        } else {
            maxHeight = 100;
        }

        // get the max/min moon illumination from the text fields
        double minIllumination, maxIllumination;
        if(!(minMoonIlluminationTextField.getText().trim().equals(""))) {
            minIllumination = Double.parseDouble(minMoonIlluminationTextField.getText());
        } else {
            minIllumination = 0;
        }
        if(!(maxMoonIlluminationTextField.getText().trim().equals(""))) {
            maxIllumination = Double.parseDouble(maxMoonIlluminationTextField.getText());
        } else {
            maxIllumination = 100;
        }

        // add the days to the search result
        // iterate through all days within date range
        for(int day = startDay; day <= endDay; day++) {
            boolean addDay = false;
            // check for moon illumination
            if(!(yearData.get(day - 1).getMoonData().getIllumination() >= minIllumination
                    && yearData.get(day - 1).getMoonData().getIllumination() <= maxIllumination)) {
                continue; // skip this day
            }
            // iterating through the tides for the day until a match is found
            for(TideData tide : yearData.get(day - 1).getTides()) {
                if((tide.getTime().toLocalTime().isAfter(startTime) && tide.getTime().toLocalTime().isBefore(endTime)) // check the time
                        && ((tide.isHighTide() && high) || ((!tide.isHighTide() && low)))                              // check tide type
                        && ((tide.getTideHeight() >= minHeight) && (tide.getTideHeight() <= maxHeight))) {             // check tide height
                    // a tide matching search criteria has been found, exit loop
                    addDay = true;
                    break;
                }
            }
            if(addDay) searchResults.add(yearData.get(day - 1));
        }
        // clear the days list
        days.clear();
        // add the days from the search to the days list
        days.addAll(searchResults);
    }

    @FXML
    private void onViewAllButtonPressed() {
        DayManager manager = DayManager.getInstance();
        // add all days to the ObservableList
        if(manager.getYearData() != null) {
            // clear the days list
            days.clear();
            // add the days to the days list
            days.addAll(manager.getYearData());
        } else {
            System.out.println("Error: No data available.");
        }
    }
}
