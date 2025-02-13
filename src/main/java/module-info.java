module org.kelbymannigel.tidedatatracker {
    requires java.naming;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens org.kelbymannigel.tidedatatracker to org.hibernate.orm.core, javafx.fxml;
    exports org.kelbymannigel.tidedatatracker;
}