package com.easternmind.savemanipulator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    public void LoadDialog(ActionEvent event) throws IOException, InvalidEasternMindFileException {
        ManipulatorController.instance().InitializeSaveManipulator();
    }

    @FXML
    public void SaveFile(ActionEvent event) throws IOException {
        ManipulatorController.instance().SaveFile();
    }

    @FXML
    public void CloseFile(ActionEvent event){
        ManipulatorController.instance().CloseFile();
    }

    @FXML
    public void Quit(ActionEvent event){
        Platform.exit();
    }


    @FXML
    public void Shout() {
        System.out.println("I live!");
    }
}
