package com.easternmind.savemanipulator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    @FXML
    private MenuItem save, saveAs, close;

    @FXML
    private ChoiceBox<String> itemBox;

    @FXML
    public CheckBox possesionCheckbox;

    @FXML
    public AnchorPane anchorPane;

    @FXML
    public void LoadDialog(ActionEvent event) throws IOException, InvalidEasternMindFileException {
        SaveManipulator.instance().Reset();

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT Files","*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Load Save File");

        File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());
        if(selectedFile != null) {
            SaveManipulator.instance().SetFileName(
                    selectedFile.getPath()
            );
            SaveManipulator.instance().LoadFile();
        }
        if(SaveManipulator.instance().checkIfFileIsLoaded()) {
            //Enable menu buttons
            save.setDisable(false);
            saveAs.setDisable(false);
            close.setDisable(false);

            //Add Items to itemBox
            ObservableList<String> list = FXCollections.observableArrayList(SaveManipulator.instance().itemNames);
            itemBox.setItems(list);
        }
    }

    @FXML
    public void SetCheckbox(){
        if(SaveManipulator.instance().checkIfFileIsLoaded()){
            //possesionCheckbox.setSelected();
        }
    }

    @FXML
    public void SaveFile(ActionEvent event) throws IOException {
        SaveManipulator.instance().WriteFile();
        SaveManipulator.instance().SetItem(itemBox.getValue());
    }

    @FXML
    public void SaveAsFile(ActionEvent event) throws IOException{
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT Files","*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save as...");

        File saveFile = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
        if(saveFile != null){
            SaveManipulator.instance().SetFileName(saveFile.getPath());
            SaveManipulator.instance().WriteFile();
        }
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
