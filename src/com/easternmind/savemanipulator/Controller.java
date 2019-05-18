package com.easternmind.savemanipulator;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Controller {

    @FXML
    private MenuItem save, saveAs, close;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void LoadDialog(ActionEvent event) throws IOException, InvalidEasternMindFileException {
        SaveManipulator.instance().Reset();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Save File");
        File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if(selectedFile != null) {
            SaveManipulator.instance().SetFileName(
                    selectedFile.getPath()
            );
            SaveManipulator.instance().LoadFile();
        }
        if(SaveManipulator.instance().checkIfFileIsLoaded()) {
            save.setDisable(false);
            saveAs.setDisable(false);
            close.setDisable(false);
        }
    }

    @FXML
    public void SaveFile(ActionEvent event) throws IOException {
        SaveManipulator.instance().WriteFile();
    }

    @FXML
    public void CloseFile(ActionEvent event){
        SaveManipulator.instance().Reset();
        save.setDisable(true);
        saveAs.setDisable(true);
        close.setDisable(true);
        System.out.println("File closed!");
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
