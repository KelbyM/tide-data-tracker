package org.kelbymannigel.tidedatatracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class DayViewController {

    // CONTROLS

    @FXML
    private Label dateLabel;

    // LAYOUTS

    @FXML
    private VBox tideDataVBox,
                 moonDataVBox,
                 dayVBox;

    // ACTIONS

    public void setDayInformation(Day day) {
        // set dateLabel
        dateLabel.setText(day.getDate().toString());
        // add the tide data to the tideDataVBox
        for(TideData data : day.getTides()) {
            String tideType = data.isHighTide() ? "High" : "Low ";
            String line = String.format("%02d:%02d %.2fft. %s", data.getTime().getHour(), data.getTime().getMinute(), data.getTideHeight(), tideType);
            tideDataVBox.getChildren().add(new Label(line));
        }
        // add the moon data to the moonDataVBox
        MoonData moonData = day.getMoonData();
        // format the text
        String illumination = "Illumination: " + moonData.getIllumination() + "%";
        String age = String.format("Days Since New Moon: %.1f", moonData.getDaysSinceNewMoon());
        String phase = "Current Phase: " + moonData.processPhaseName();
        // make each string the same size
        int textWidth = 30;
        illumination = String.format("%-" + textWidth + "s", illumination);
        age = String.format("%-" + textWidth + "s", age);
        phase = String.format("%-" + textWidth + "s", phase);
        // add each string as a label to the moonDataVBox
        moonDataVBox.getChildren().add(new Label(illumination));
        moonDataVBox.getChildren().add(new Label(age));
        moonDataVBox.getChildren().add(new Label(phase));
        // create a border to visually separate it
        Border border = new Border(new javafx.scene.layout.BorderStroke(Color.GRAY, javafx.scene.layout.BorderStrokeStyle.SOLID, null, new javafx.scene.layout.BorderWidths(2)));
        dayVBox.setBorder(border);
    }
}
