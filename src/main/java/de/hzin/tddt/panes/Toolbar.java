package de.hzin.tddt.panes;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;

/**
 * Created by Aron on 12.07.2016.
 */
public class Toolbar extends ToolBar {
    Button btnCompile = new Button("Compile");
    Button btnSave = new Button("Save");
    Button btnNext = new Button("Next");
    Button btnPrevious = new Button("Previous");

    public Toolbar(){
        getItems().addAll(btnCompile,btnSave,btnNext,btnPrevious);
    }
}
