package de.hzin.tddt;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    private static Stage primaryStage;
    private Parent menupane;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TDDT");
        menupane= FXMLLoader.load(getClass().getResource("/SceneBuilderMenu.fxml"));
        System.out.println(getClass().getResource("/SceneBuilderMenu.fxml").toString());

        Scene scene = new Scene(menupane);
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            if(ConfirmBox.display("EXIT","Are you sure?")) {
                primaryStage.close();
            }
        });
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}