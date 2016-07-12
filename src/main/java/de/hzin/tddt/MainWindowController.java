package de.hzin.tddt;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import de.hzin.tddt.objects.State;
import de.hzin.tddt.panes.ExerciseView;
import de.hzin.tddt.util.Charts;
import de.hzin.tddt.util.Compilation;
import de.hzin.tddt.util.TimeKeeper;
import de.hzin.tddt.util.XMLHandler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Time;
import java.util.*;
import java.util.List;

import static de.hzin.tddt.JavaKeywordsAsync.computeHighlighting;

public class MainWindowController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label timecounter;

    @FXML
    private Label aktphase;

    @FXML
    public TextArea logTextArea;

    private Exercises exercises;
    private String[] contents = new String[2];
    private State state = State.TEST;
    private CodeArea codeArea = new CodeArea();
    private Timeline time;
    private int sekunden;
    private TimeKeeper timeKeeper = new TimeKeeper();

    @FXML
    public void initialize() throws URISyntaxException {
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });
        mainPane.getStylesheets().add("java-keywords.css");
        mainPane.setCenter(codeArea);
    }

    public void onMenuOpenExercisePressed() {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("Catalog/"));
        fileChooser.setTitle("Öffne Übungsdatei");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Übungsdatei", "*.xml"));

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            openExercise(file);
        }
    }

    public void saveCurrentFile(){
        ExerciseClass currentClass = exercises.getCurrentExercise().getCurrentClass();
        if(currentClass.isCurrentTest()){
            currentClass.getTest().setCode(codeArea.getText());
        }
        else{
            currentClass.setCode(codeArea.getText());
        }
    }

    public void openExercise(File file) {
        try {
            exercises = XMLHandler.unmarshal(file);
            codeArea.replaceText(exercises.getCurrentExercise().getClasses().get(0).getCode());
            ExerciseView exerciseView = new ExerciseView(exercises, file.getName(), codeArea);
            mainPane.setLeft(exerciseView);
            timeKeeper = new TimeKeeper();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void compile() {
        // Compiler Integration
        Compilation compiler = new Compilation(exercises, logTextArea,contents);
        if(exercises != null){
            Exercise exercise = exercises.getCurrentExercise();
            List<ExerciseClass> exerciseClass = exercise.getClasses();
            exerciseClass.get(0).setCode(codeArea.getText());
        }
        //compiler.runCompilation();
    }


    public void starteTimer() {
        sekunden = 0;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden++;
            timecounter.setText("Zeit:" + String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void stopZeit() {
        time.stop();
    }

    public void startebabystepTimer() {
        sekunden = 180;
        time = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            sekunden--;
            if (sekunden == 0) {
                stopZeit();
                timecounter.setText("Zeit:" + String.valueOf(sekunden));
            }
            timecounter.setText("Zeit:" + String.valueOf(sekunden));
        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void green() {
        if (time != null) stopZeit();
        aktphase.setText("GREEN ; Bearbeite deinen Code");
        aktphase.setStyle("-fx-text-fill: green;");
        starteTimer();
        state = State.TEST;
        System.out.println(state);
        timeKeeper.changeStateTo(state);
    }

    public void red() {
        if (time != null) stopZeit();
        aktphase.setText("RED ; Bearbeite deine Tests");
        aktphase.setStyle("-fx-text-fill: red;");
        starteTimer();
        state = State.CODE;
        System.out.println(state);
        timeKeeper.changeStateTo(state);
    }

    public void refre() {
        if (time != null) stopZeit();
        aktphase.setText("REFRACTOR ; Code verbessern");
        aktphase.setStyle("-fx-text-fill: black;");
        starteTimer();
        state = State.REFACTOR;
        System.out.println(state);
        timeKeeper.changeStateTo(state);
    }

    public void openfile() {
        File txtfile = new File("Help.txt");
        if (txtfile.exists()) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(txtfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void chartDisplay(){
        timeKeeper.refreshTime(state);
        Charts.display(timeKeeper.getTimeTest(), timeKeeper.getTimeCode(), timeKeeper.getTimeRefactor());
    }
}
