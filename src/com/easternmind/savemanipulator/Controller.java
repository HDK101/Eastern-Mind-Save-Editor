package com.easternmind.savemanipulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    public void LoadDialog(ActionEvent event) throws IOException {
        ManipulatorController.instance().InitializeSaveManipulator();
    }

    @FXML
    public void SaveFile(ActionEvent event) throws IOException {
        ManipulatorController.instance().SaveFile();
    }


    @FXML
    public void Shout() {
        System.out.println("I live!");
    }
}
