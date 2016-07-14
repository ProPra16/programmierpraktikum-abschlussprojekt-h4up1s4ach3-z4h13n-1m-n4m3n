package de.hzin.tddt.panes;

/**
 * Created by constantin on 12.07.16.
 */

import de.hzin.tddt.util.ErrorCounter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;

public class Charts {

    private ErrorCounter errorCounter = new ErrorCounter();

    public void display(double red, double green, double refactor) {
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
        Label errorStats = new Label();
        errorStats.setStyle("-fx-font-family: monospace");
        errorStats.setText("ERRORS:                                \n" +
                "Syntax: " + errorCounter.getSyntax() + "\n" +
                "Identifier: " + errorCounter.getIdentifiers() + "\n" +
                "Computation: " + errorCounter.getComputation() + "\n" +
                "Return Statements: " + errorCounter.getReturnStatements() + "\n" +
                "Access to Static Entities: " + errorCounter.getAccessToStaticEntities());

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);

        chart.setTitle("Tracking Data");

        HBox hBox = new HBox();
        hBox.getChildren().add(chart);
        hBox.getChildren().add(separator);
        hBox.getChildren().add(createBarchart());
        Group group = new Group();

        group.getChildren().add(hBox);
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

    public ErrorCounter getErrorCounter() {
        return errorCounter;
    }

    public BarChart createBarchart() {
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
                new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Error Summary");
        //xAxis.setLabel("Error Type");
        yAxis.setLabel("Error Count");

        XYChart.Series series = new XYChart.Series();
        series.setName("Errors");
        series.getData().add(new XYChart.Data("Syntax", errorCounter.getSyntax()));
        series.getData().add(new XYChart.Data("Computation", errorCounter.getComputation()));
        series.getData().add(new XYChart.Data("Return Statements", errorCounter.getReturnStatements()));
        series.getData().add(new XYChart.Data("Access to Static Entities", errorCounter.getAccessToStaticEntities()));
        series.getData().add(new XYChart.Data("Identifiers", errorCounter.getIdentifiers()));
        bc.getData().addAll(series);
        return bc;
    }

}
