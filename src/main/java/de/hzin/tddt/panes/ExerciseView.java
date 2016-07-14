package de.hzin.tddt.panes;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

/**
 * @author Aron Weisermann
 */
public class ExerciseView extends TreeView<String> {
    public ExerciseView(Exercises exercises) {
        List<Exercise> exerciseList = exercises.getExercisesList();
        TreeItem<String> rootNode =
                new TreeItem<String>(exercises.getFile().getName());
        rootNode.setExpanded(true);

        for (Exercise exercise : exerciseList) {
            TreeItem<String> exNode = new TreeItem<String>(exercise.getName());
            for (ExerciseClass exClass : exercise.getClasses()) {
                TreeItem<String> clNode = new TreeItem<String>(exClass.getName());
                TreeItem<String> teNode = new TreeItem<String>(exClass.getTest().getName());
                exNode.getChildren().addAll(clNode, teNode);
            }
            rootNode.getChildren().add(exNode);
        }
        setRoot(rootNode);
    }

    /*public ExerciseView(Exercises exercises, String exercisesName, CodeArea codeArea) {
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
    }*/
}