
import com.easternmind.savemanipulator.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    //Eastern Mind Save Editor Class
    public static SaveManipulator easternSave;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("com/easternmind/savemanipulator/test.fxml"));
        primaryStage.setTitle("Eastern Mind Save Editor");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.show();

        Scene scene = primaryStage.getScene();
        //System.out.println(choiceBox.getValue());
    }

    public static void main(String[] args) {

        launch(args);
    }
}

