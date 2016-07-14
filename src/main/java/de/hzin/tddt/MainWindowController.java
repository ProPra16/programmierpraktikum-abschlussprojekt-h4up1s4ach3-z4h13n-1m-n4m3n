package de.hzin.tddt;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import de.hzin.tddt.objects.State;
import de.hzin.tddt.panes.BabystepTimer;
import de.hzin.tddt.panes.ExerciseView;
import de.hzin.tddt.panes.Toolbar;
import de.hzin.tddt.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javax.xml.bind.JAXBException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static de.hzin.tddt.JavaKeywordsAsync.computeHighlighting;

public class MainWindowController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button redBUT;

    @FXML
    private Button backredBUT;

    @FXML
    private Button greenBUT;

    @FXML
    private Button refacBUT;

    @FXML
    private VBox rightContainer;

    private BabystepTimer babystepTimer;

    @FXML
    private Label aktphase;

    @FXML
    public TextArea logTextArea;

    @FXML
    private VBox topContainer;

    public Exercises exercises;
    private String[] contents = new String[2];
    private State state = State.TEST;
    private CodeArea codeArea = new CodeArea();
    private TimeKeeper timeKeeper = new TimeKeeper();
    private Charts charts = new Charts();

    @FXML
    public void initialize() throws URISyntaxException {
        babystepTimer = new BabystepTimer();
        rightContainer.getChildren().add(0,babystepTimer);

        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

        codeArea.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .subscribe(change -> {
                    codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
                });
        mainPane.getStylesheets().add("java-keywords.css");
        mainPane.setCenter(codeArea);
        this.redBUT.setDisable(true);
        this.refacBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.greenBUT.setDisable(false);
        //topContainer.getChildren().add(new Toolbar());
    }

    public void onSaveFilePressed() {
        exercises.saveExercises();
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

    public void saveCurrentFile() {
        ExerciseClass currentClass = exercises.getCurrentExercise().getCurrentClass();
        if (currentClass.isCurrentTest()) {
            currentClass.getTest().setCode(codeArea.getText());
        } else {
            currentClass.setCode(codeArea.getText());
        }
    }

    public void replaceCodeAreaTextToCurrent() {
        ExerciseClass currentClass = exercises.getCurrentExercise().getCurrentClass();
        if (currentClass.isCurrentTest()) {
            codeArea.replaceText(currentClass.getTest().getCode());
        } else {
            codeArea.replaceText(currentClass.getCode());
        }
    }

    public void openExercise(File file) {
        try {
            exercises = XMLHandler.unmarshal(file);
            exercises.setFile(file);
            codeArea.replaceText(exercises.getCurrentExercise().getClasses().get(0).getTest().getCode());
            ExerciseView exerciseView = new ExerciseView(this);
            mainPane.setLeft(exerciseView);
            timeKeeper = new TimeKeeper();
            this.redBUT.setDisable(true);
            this.refacBUT.setDisable(true);
            this.backredBUT.setDisable(true);
            this.greenBUT.setDisable(false);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void compile() {
        // Compiler Integration
        Compilation compiler = new Compilation(exercises, logTextArea, contents);
        if (exercises != null) {
            Exercise exercise = exercises.getCurrentExercise();
            List<ExerciseClass> exerciseClass = exercise.getClasses();
            exerciseClass.get(0).setCode(codeArea.getText());
            saveCurrentFile();
        }
        ErrorCounter currentErrorCounter = compiler.getErrorCounter();
        charts.getErrorCounter().addErrorCounter(currentErrorCounter.getSyntax(),currentErrorCounter.getIdentifiers(),currentErrorCounter.getComputation(),currentErrorCounter.getReturnStatements(),currentErrorCounter.getAccessToStaticEntities());
        //compiler.runCompilation();
    }


    public void green() {
        if(exercises == null){
            logTextArea.setText("No exercise selected");
        }
        else {
            babystepTimer.stopTimer();
            aktphase.setText("GREEN ; Bearbeite deinen Code");
            aktphase.setStyle("-fx-text-fill: green;");
            saveCurrentFile();
            exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(false);
            replaceCodeAreaTextToCurrent();
            if (babystepTimer != null) {
                int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
                babystepTimer.startTimer(time);
            }
            state = State.CODE;
            timeKeeper.changeStateTo(state);
            this.backredBUT.setDisable(false);
            this.redBUT.setDisable(true);
            this.greenBUT.setDisable(true);
            this.refacBUT.setDisable(false);
        }

    }

    public void red() {
        if (babystepTimer != null) babystepTimer.stopTimer();
        aktphase.setText("RED ; Bearbeite deine Tests");
        aktphase.setStyle("-fx-text-fill: red;");
        saveCurrentFile();
        exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(true);
        replaceCodeAreaTextToCurrent();
        if (babystepTimer != null) {
            int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
            babystepTimer.startTimer(time);
        }
        state = State.TEST;
        timeKeeper.changeStateTo(state);
        this.redBUT.setDisable(true);
        this.refacBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.greenBUT.setDisable(false);

    }

    public void refac() {
        if (babystepTimer != null) babystepTimer.stopTimer();
        aktphase.setText("REFRACTOR ; Code verbessern");
        aktphase.setStyle("-fx-text-fill: black;");
        saveCurrentFile();
        exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(false);
        replaceCodeAreaTextToCurrent();
        if (babystepTimer != null) {
            int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
            babystepTimer.startTimer(time);
        }
        state = State.REFACTOR;
        timeKeeper.changeStateTo(state);
        this.greenBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.redBUT.setDisable(false);
        this.refacBUT.setDisable(true);


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

    public void chartDisplay() {
        timeKeeper.refreshTime();
        charts.display(timeKeeper.getTimeTest(), timeKeeper.getTimeCode(), timeKeeper.getTimeRefactor());
    }

}
