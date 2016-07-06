package de.hzin.tddt.panes;

import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseJavaFile;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * @author Aron Weisermann
 */
public class ExerciseView extends TitledPane {

    TitledPane[] exercisePanes;
    ListView listView = new ListView();

    public ExerciseView(List<Exercise> exercises, String exerciseName) {
        setText(exerciseName);

        exercisePanes = new TitledPane[exercises.size()];

        for (int i = 0; i < exercises.size(); i++) {
            ListView javaFilesListView = new ListView();
            for(ExerciseJavaFile cur : exercises.get(i).getClasses())
            {
                javaFilesListView.getItems().add(cur.getName());
            }
            for(ExerciseJavaFile cur : exercises.get(i).getTests())
            {
                javaFilesListView.getItems().add(cur.getName());
            }
            exercisePanes[i] = new TitledPane(exercises.get(i).getName(),javaFilesListView);
        }
        VBox group = new VBox();
        group.getChildren().addAll(exercisePanes);
        setContent(group);
    }
}
