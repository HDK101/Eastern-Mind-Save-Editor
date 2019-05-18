package com.easternmind.savemanipulator;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class ManipulatorController {
    //Stage for FileChooser
    public Stage primaryStage;

    private static ManipulatorController self;
    public static ManipulatorController instance(){
        if(self == null){
             self = new ManipulatorController();
        }
        return self;
    }

    //Eastern Mind Save Editor Class
    public SaveManipulator easternSave;

    public ManipulatorController(){
        easternSave = new SaveManipulator();
    }


    public void InitializeSaveManipulator() throws IOException {
        easternSave = new SaveManipulator();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Save File");
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if(selectedFile != null) {
            easternSave.SetFileName(
                    fileChooser.showOpenDialog(primaryStage).getPath()
            );
            easternSave.LoadFile();
        }
    }

    public void SaveFile() throws IOException{
        easternSave.WriteFile();
    }
}
