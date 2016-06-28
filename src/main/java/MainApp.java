import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        String[] names = new String[2];
        names[0] = "LeapYear";
        names[1] = "LeapYearTest";
        Compilation test = new Compilation(names);
        Group root = new Group();
        primaryStage.setScene(new Scene(root, 100, 100));
        primaryStage.show();
    }
}