package de.hzin.tddt.util;

/**
 * Created by constantin on 12.07.16.
 */

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

public class Charts {

    public static void display(double red, double green, double refactor) {
        int normRed = (int) ( red*100/(red+green+refactor));
        int normGreen = (int) (green*100/(red+green+refactor));
        int normRefactor = (int) (refactor*100/(red+green+refactor));
        int[] times = new int[3];
        times[0] = normRed;
        times[1] = normGreen;
        times[2] = normRefactor;

        Stage window = new Stage();
        window.setTitle("Tracking Chart");
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("RED\n"+Integer.toString((int)red)+" s", normRed),
                        new PieChart.Data("GREEN\n"+Integer.toString((int)green)+" s", normGreen),
                        new PieChart.Data("REFACTOR\n"+Integer.toString((int)refactor)+" s", normRefactor));
        final PieChart chart = new PieChart(pieChartData);
        chart.setLegendVisible(false);
        applyCustomColorSequence(
                pieChartData,
                "red",
                "green",
                "darkblue"
        );

        chart.setTitle("Tracking Data");
        Group group = new Group();

        group.getChildren().add(chart);
        Scene scene = new Scene(group);
        window.setScene(scene);
        showValues(chart,group,times);
        // Close if Focus lost
        window.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue) {
                    window.close();
                }
            }
        });

        window.showAndWait();
    }

    private static void applyCustomColorSequence(
            ObservableList<PieChart.Data> pieChartData,
            String... pieColors) {
        int i = 0;
        for (PieChart.Data data : pieChartData) {
            data.getNode().setStyle(
                    "-fx-pie-color: " + pieColors[i % pieColors.length] + ";"
            );
            i++;
        }
    }
    private static void showValues(PieChart chart, Group group, int[] time){
        Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font: 24 arial;");
        int i = 0;
        for (final PieChart.Data data : chart.getData()) {
            int finalI = i;
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        caption.setTranslateX(e.getSceneX()+10);
                        caption.setTranslateY(e.getSceneY()+10);
                        caption.setText(Integer.toString(time[finalI]) + "%");

                    });
            i++;
            data.getNode().addEventHandler(MouseEvent.MOUSE_RELEASED,
                    e -> caption.setText(""));

        }
        group.getChildren().add(caption);
    }
}
