package de.hzin.tddt;

import de.hzin.tddt.panes.ConfirmBox;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    private static Stage primaryStage;
    private Parent mainPane;

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TDDT");
        mainPane = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
        //System.out.println(getClass().getResource("/MainWindow.fxml").toString());

        Scene scene = new Scene(mainPane);
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
