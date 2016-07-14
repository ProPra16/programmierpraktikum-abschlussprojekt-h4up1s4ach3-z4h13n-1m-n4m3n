package de.hzin.tddt;

import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import de.hzin.tddt.objects.State;
import de.hzin.tddt.panes.BabystepTimer;
import de.hzin.tddt.panes.Charts;
import de.hzin.tddt.panes.ExerciseView;
import de.hzin.tddt.util.*;
import javafx.fxml.FXML;
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
import java.io.File;
import java.net.URISyntaxException;

import static de.hzin.tddt.JavaKeywordsAsync.computeHighlighting;

public class MainWindowController {

    @FXML
    private BorderPane mainPane;

    @FXML
    public Label lblDescription;

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

    @FXML
    private Label aktphase;

    @FXML
    public TextArea logTextArea;


    public BabystepTimer babystepTimer;
    private CodeArea codeArea = new CodeArea();
    private TimeKeeper timeKeeper = new TimeKeeper();
    private Charts charts = new Charts();

    private boolean isFirstTestWritten = false;
    private boolean isFirstTestWrittenButReturned = false;
    public Exercises exercises;
    private String[] contents = new String[2];
    private State state = State.TEST;

    @FXML
    public void initialize() throws URISyntaxException {
        babystepTimer = new BabystepTimer(this);
        rightContainer.getChildren().add(0, babystepTimer);

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
    }

    public void onSaveFilePressed() {
        if(exercises!=null){
            exercises.saveExercises();
            logTextArea.appendText("Erfolgreich unter " + exercises.getFile().getAbsolutePath() + " gespeichert.");
        }
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
            rightContainer.setVisible(true);
            this.redBUT.setDisable(true);
            this.refacBUT.setDisable(true);
            this.backredBUT.setDisable(true);
            this.greenBUT.setDisable(false);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public Compilation compile() {
        if(exercises == null){
            // Compiler Integration
            saveCurrentFile();
            Compilation compiler = new Compilation(exercises, logTextArea, contents);
            ErrorCounter currentErrorCounter = compiler.getErrorCounter();
            charts.getErrorCounter().addErrorCounter(currentErrorCounter.getSyntax(), currentErrorCounter.getIdentifiers(), currentErrorCounter.getComputation(), currentErrorCounter.getReturnStatements(), currentErrorCounter.getAccessToStaticEntities());
            //compiler.runCompilation();
            return compiler;
        }
        else return null;
    }


    public void green() {
        Compilation compilation = compile();
        if (isFirstTestWritten) {
            if (compilation.hasCompileErrors()) return;
            if (compilation.getNumberOfFailedTests() != 1) return;
        } else {
            isFirstTestWrittenButReturned = true;
        }
        isFirstTestWritten = true;
        if (exercises == null) {
            logTextArea.setText("No exercise selected");
        } else {
            babystepTimer.stopTimer();
            aktphase.setText("GREEN ; Bearbeite deinen Code");
            aktphase.setStyle("-fx-text-fill: green;");
            saveCurrentFile();
            exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(false);
            replaceCodeAreaTextToCurrent();
            if (exercises.getCurrentExercise().getConfig().getBabysteps().getValue().contains("True")) {
                babystepTimer.setVisible(true);
                int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
                babystepTimer.startTimer(time);
            }
            state = State.CODE;
            if (exercises.getCurrentExercise().getConfig().getTimetracking().getValue().contains("True")) {
                timeKeeper.changeStateTo(state);
            }
            this.backredBUT.setDisable(false);
            this.redBUT.setDisable(true);
            this.greenBUT.setDisable(true);
            this.refacBUT.setDisable(false);
        }
    }

    public void backToRed() {
        if (isFirstTestWrittenButReturned) isFirstTestWritten = false;
        babystepTimer.stopTimer();
        aktphase.setText("RED ; Bearbeite deine Tests");
        aktphase.setStyle("-fx-text-fill: red;");
        exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(true);
        replaceCodeAreaTextToCurrent();
        if (exercises.getCurrentExercise().getConfig().getBabysteps().getValue().contains("True")) {
            babystepTimer.setVisible(true);
            int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
            babystepTimer.startTimer(time);
        }
        state = State.TEST;
        if (exercises.getCurrentExercise().getConfig().getTimetracking().getValue().contains("True")) {
            timeKeeper.changeStateTo(state);
        }
        this.redBUT.setDisable(true);
        this.refacBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.greenBUT.setDisable(false);
    }

    public void red() {
        Compilation compilation = compile();
        if (compilation.hasCompileErrors()) return;
        if (compilation.getNumberOfFailedTests() > 0) return;
        if (babystepTimer != null) babystepTimer.stopTimer();
        aktphase.setText("RED ; Bearbeite deine Tests");
        aktphase.setStyle("-fx-text-fill: red;");
        saveCurrentFile();
        exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(true);
        replaceCodeAreaTextToCurrent();
        if (exercises.getCurrentExercise().getConfig().getBabysteps().getValue().contains("True")) {
            babystepTimer.setVisible(true);
            int time = exercises.getCurrentExercise().getConfig().getBabysteps().getTime();
            babystepTimer.startTimer(time);
        }
        state = State.TEST;
        if (exercises.getCurrentExercise().getConfig().getTimetracking().getValue().contains("True")) {
            timeKeeper.changeStateTo(state);
        }
        this.redBUT.setDisable(true);
        this.refacBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.greenBUT.setDisable(false);

    }

    public void refac() {
        Compilation compilation = compile();
        if (compilation.hasCompileErrors()) return;
        if (compilation.getNumberOfFailedTests() > 0) return;
        isFirstTestWrittenButReturned = false;
        if (babystepTimer != null) babystepTimer.stopTimer();
        aktphase.setText("REFRACTOR ; Code verbessern");
        aktphase.setStyle("-fx-text-fill: black;");
        saveCurrentFile();
        exercises.getCurrentExercise().getCurrentClass().setIsCurrentTest(false);
        replaceCodeAreaTextToCurrent();

        babystepTimer.setVisible(false);

        state = State.REFACTOR;

        if (exercises.getCurrentExercise().getConfig().getTimetracking().getValue().contains("True")) {
            timeKeeper.changeStateTo(state);
        }

        this.greenBUT.setDisable(true);
        this.backredBUT.setDisable(true);
        this.redBUT.setDisable(false);
        this.refacBUT.setDisable(true);


    }

    public void chartDisplay() {
        timeKeeper.refreshTime();
        charts.display(timeKeeper.getTimeTest(), timeKeeper.getTimeCode(), timeKeeper.getTimeRefactor());
    }

}
