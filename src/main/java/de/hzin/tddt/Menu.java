package de.hzin.tddt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Menu {

    @FXML
    public static Label timecounter= new Label("Zeit");
    @FXML
    private Pane codePane;

    CodeEditor codeEditor;

    @FXML
    public void initialize() {
        codeEditor = new CodeEditor(
                "import static org.junit.Assert.*;\n" +
                "import org.junit.Test;\n" +
                "public class RomanNumbersTest {\n" +
                "   @Test\n" +
                "   public void testSomething() {\n" +
                "   }\n" +
                "}");
        codePane.getChildren().add(codeEditor);
    }


}
