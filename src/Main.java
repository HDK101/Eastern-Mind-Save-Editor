
import java.io.*;
import com.easternmind.savemanipulator.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample/sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {
        SaveManipulator easternSave = new SaveManipulator();
        easternSave.SetFileName("input");

        easternSave.LoadFile();
        //Set item 2,3,4 availability
        //easternSave.SetItem(18);
        //easternSave.SetItem(8);
        //easternSave.SetItem(9);
        easternSave.SetAllItem(true);
        easternSave.SetHelixFloor(6);
        //Set current frame
        easternSave.SetFrame(20);

        //Set character to BYOU
        easternSave.SetCharacter(1);

        easternSave.SetTongNouSeal(true);

        //Set location to TongNouFace and the way to market changed to MingKenField
        easternSave.SetLocation(SaveManipulator.LocationList.CentralMountain);
        //easternSave.SetOutLocation(SaveManipulator.OutLocation.MonChien);

        //easternSave.SetAllItem();
        easternSave.WriteFile();
        launch(args);
    }
}

