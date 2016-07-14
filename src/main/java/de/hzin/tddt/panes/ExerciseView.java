package de.hzin.tddt.panes;

import de.hzin.tddt.MainWindowController;
import de.hzin.tddt.objects.Exercise;
import de.hzin.tddt.objects.ExerciseClass;
import de.hzin.tddt.objects.Exercises;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;

public class ExerciseView extends TreeView {

    public ExerciseView(MainWindowController controller) {
        Exercises exercises = controller.exercises;
        List<Exercise> exerciseList = exercises.getExercisesList();
        TreeItem rootNode = new TreeItem<>(exercises.getFile().getName());
        ToggleGroup toggleGroup = new ToggleGroup();

        for (int i = 0; i < exerciseList.size(); i++) {
            Exercise exercise = exerciseList.get(i);
            RadioButton exRadioBtn = new RadioButton(exercise.getName());
            exRadioBtn.setToggleGroup(toggleGroup);

            TreeItem<RadioButton> exNode = new TreeItem<>(exRadioBtn);
            for (ExerciseClass exClass : exercise.getClasses()) {
                TreeItem clNode = new TreeItem<>(exClass.getName());
                TreeItem teNode = new TreeItem<>(exClass.getTest().getName());

                exNode.getChildren().addAll(clNode, teNode);
            }

            exRadioBtn.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event) -> {
                if (isChangeExerciseConfirmed()) {
                    exRadioBtn.setSelected(true);
                    event.consume();
                }
            });

            rootNode.getChildren().add(exNode);
        }
        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (toggleGroup.getSelectedToggle() != null) {
                int selectedIndex = toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle());
                controller.saveCurrentFile();
                exercises.setCurrentIndex(selectedIndex);
                controller.replaceCodeAreaTextToCurrent();
                controller.lblDescription.setText("Beschreibung: " + exercises.getCurrentExercise().getDescription());
                if (exercises.getCurrentExercise().getConfig().getBabysteps().getValue().contains("True")) {
                    controller.babystepTimer.setVisible(true);
                    controller.babystepTimer.startTimer(exercises.getCurrentExercise().getConfig().getBabysteps().getTime());
                }
                else {
                    controller.babystepTimer.setVisible(false);
                    controller.babystepTimer.stopTimer();
                }
            }
        });

        rootNode.setExpanded(true);
        setRoot(rootNode);
    }

    public boolean isChangeExerciseConfirmed() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Sind Sie sicher, dass Sie die Übung wechseln möchten?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get().equals(ButtonType.OK)) {
            return true;
        } else {
            return false;
        }
    }
}