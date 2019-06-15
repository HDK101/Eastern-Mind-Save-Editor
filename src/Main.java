
import com.easternmind.savemanipulator.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("com/easternmind/savemanipulator/easternui.fxml"));
        primaryStage.setTitle("Eastern Mind Save Editor");
        primaryStage.setScene(new Scene(root, 390, 275));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}

