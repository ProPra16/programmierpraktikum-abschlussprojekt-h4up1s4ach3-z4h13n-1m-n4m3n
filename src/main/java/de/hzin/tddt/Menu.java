package de.hzin.tddt;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javax.xml.bind.TypeConstraintException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static de.hzin.tddt.JavaKeywordsAsync.computeHighlighting;

public class Menu {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label timecounter;

    @FXML
    private Label aktphase;

    @FXML
    public TextArea logTextArea;

    CodeEditor codeEditor;

    @FXML
    public void initialize() throws URISyntaxException {
        /*codeEditor = new CodeEditor(
                "import static org.junit.Assert.*;\n" +
                "import org.junit.Test;\n" +
                "public class RomanNumbersTest {\n" +
                "   @Test\n" +
                "   public void testSomething() {\n" +
                "   }\n" +
                "}");

        codeEditor.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);

        mainPane.setCenter(codeEditor);

        logTextArea.setText("Erfolg!");*/

        CodeArea codeArea = new CodeArea();
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });
        mainPane.getStylesheets().add("java-keywords.css");
        mainPane.setCenter(codeArea);


    }

    public void compile(){
        // Compiler Integration
        String[] classes = new String[2];
        classes[0] = "LeapYear";
        classes[1] = "LeapYearTest";
        Compilation compiler = new Compilation(classes, logTextArea);
        compiler.runCompilation();
    }

    static Timeline time;
    static int sekunden;


    public void starteTimer() {
        sekunden =0;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden++;
            timecounter.setText("Zeit:" +String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void stopZeit() {
        time.stop();
    }

    public void startebabystepTimer(){
        sekunden = 180;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden--;
            if(sekunden ==0){
                stopZeit();
                timecounter.setText("Zeit:" +String.valueOf(sekunden));
            }
            timecounter.setText("Zeit:" +String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    public void green(){
        if (time!=null) stopZeit();
        aktphase.setText("GREEN ; Bearbeite deinen Code");
        aktphase.setStyle("-fx-text-fill: green;");
        starteTimer();
    }
    public void red(){
        if (time!=null) stopZeit();
        aktphase.setText("RED ; Bearbeite deine Tests");
        aktphase.setStyle("-fx-text-fill: red;");
        starteTimer();

    }
    public void refre(){
        if (time!=null) stopZeit();
        aktphase.setText("REFRACTOR ; Code verbessern");
        aktphase.setStyle("-fx-text-fill: black;");
        starteTimer();

    }
    public void openfile(){
        File txtfile = new File("Help.txt");
        if(txtfile.exists()){
            if(Desktop.isDesktopSupported()){
                try{
                    Desktop.getDesktop().open(txtfile);
                  }
                catch(IOException e){
                    e.printStackTrace();
                 }
            }
        }
    }
}
