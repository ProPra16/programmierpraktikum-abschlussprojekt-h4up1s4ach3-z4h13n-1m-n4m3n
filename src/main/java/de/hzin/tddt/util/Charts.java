package de.hzin.tddt.util;

/**
 * Created by constantin on 12.07.16.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

public class Charts {

    public static void display(double red, double green, double refactor) {
        int normRed = (int) ( red*100/(red+green+refactor));
        int normGreen = (int) (green*100/(red+green+refactor));
        int normRefactor = (int) (refactor*100/(red+green+refactor));

        System.out.println(red + ":" + normRed + "-" + green + ":" + normGreen + "-" + refactor + ":" + normRefactor + "-");;


        Stage window = new Stage();
        window.setTitle("Tracking Chart");
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("RED", normRed),
                        new PieChart.Data("GREEN", normGreen),
                        new PieChart.Data("REFACTOR", normRefactor));
        final PieChart chart = new PieChart(pieChartData);
        chart.setLegendVisible(false);
        applyCustomColorSequence(
                pieChartData,
                "red",
                "green",
                "darkblue"
        );
        chart.setTitle("Tracking Data");
        Scene scene = new Scene(new Group());
        ((Group) scene.getRoot()).getChildren().add(chart);
        window.setScene(scene);
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
}
