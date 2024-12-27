module org.kelbymannigel.tidedatatracker {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.kelbymannigel.tidedatatracker to javafx.fxml;
    exports org.kelbymannigel.tidedatatracker;
}