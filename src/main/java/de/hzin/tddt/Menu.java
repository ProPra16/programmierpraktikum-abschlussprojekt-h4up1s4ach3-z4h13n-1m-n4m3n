package de.hzin.tddt;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URISyntaxException;

public class Menu {

    @FXML
    private BorderPane mainPane;

    @FXML
    public static Label timecounter= new Label("Zeit");

    @FXML
    private TextArea logTextArea;

    CodeEditor codeEditor;

    @FXML
    public void initialize() throws URISyntaxException {
        codeEditor = new CodeEditor(
                "import static org.junit.Assert.*;\n" +
                "import org.junit.Test;\n" +
                "public class RomanNumbersTest {\n" +
                "   @Test\n" +
                "   public void testSomething() {\n" +
                "   }\n" +
                "}");

        codeEditor.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
        mainPane.setCenter(codeEditor);

        logTextArea.setText("Erfolg!");
    }
}
