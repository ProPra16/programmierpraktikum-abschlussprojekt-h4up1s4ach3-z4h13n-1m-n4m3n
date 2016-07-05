package de.hzin.tddt;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URISyntaxException;

public class Menu {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label timecounter;

    @FXML
    private Button refacBUT;

    @FXML
    private Button greenBUT;

    @FXML
    private Button redBUT;

    @FXML
    private Button backredBUT;

    @FXML
    private Button compilationButton;

    @FXML
    private ImageView phasen;

    @FXML
    public TextArea logTextArea;

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

    public void compile(){
        // Compiler Integration
        String[] classes = new String[2];
        classes[0] = "LeapYear";
        classes[1] = "LeapYearTest";
        Compilation compiler = new Compilation(classes, logTextArea);
        compiler.runCompilation();
    }
}
