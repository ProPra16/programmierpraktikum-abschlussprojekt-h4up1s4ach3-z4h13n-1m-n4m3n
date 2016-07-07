package de.hzin.tddt.panes;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;

import java.util.List;

/**
 * @author Aron Weisermann
 */
public class ExerciseView extends TitledPane {

    private TitledPane[] exercisePanes;


    public ExerciseView(Exercises exercises, String exercisesName, CodeArea codeArea) {
        List<Exercise> exerciseList = exercises.getExercisesList();
        setText("Aktuelles " + exercisesName);
        exercisePanes = new TitledPane[exerciseList.size()];
        for (int i = 0; i < exerciseList.size(); i++) {
            ListView<String> classesListView = new ListView();
            for (ExerciseClass cur : exerciseList.get(i).getClasses()) {
                classesListView.getItems().add(cur.getName());
                classesListView.getItems().add(cur.getTest().getName());
            }
            int curI = i;
            classesListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    exercises.setCurrentIndex(curI);
                    if(classesListView.getSelectionModel().getSelectedItem().contains("Test"))
                        codeArea.replaceText(exercises.getCurrentExercise().getClasses().get(0).getTest().getCode());
                    else
                        codeArea.replaceText(exercises.getCurrentExercise().getClasses().get(0).getCode());
                }
            });
            exercisePanes[i] = new TitledPane("Übung: " + exerciseList.get(i).getName(), classesListView);
        }
        VBox group = new VBox();
        group.getChildren().addAll(exercisePanes);
        setContent(group);
    }
}