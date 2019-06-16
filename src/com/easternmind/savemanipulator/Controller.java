package com.easternmind.savemanipulator;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
    private ChoiceBox<String> itemBox,charBox,locationBox,parameterBox,secondLocationBox;
    public int selectedLocationId;

    @FXML
    public CheckBox possesionCheckbox;
    public int selectedItemId;

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

            //Add character to charBox
            list = FXCollections.observableArrayList(SaveManipulator.instance().characterNames);
            charBox.setItems(list);
            //Set to choicebox
            charBox.getSelectionModel().select(SaveManipulator.instance().GetCurrentCharacterId());
            //charBox.setValue("madness");

            //Add locations to locationBox
            list = FXCollections.observableArrayList(SaveManipulator.instance().GetLocations());
            locationBox.setItems(list);
            //Set to choicebox
            locationBox.getSelectionModel().select(SaveManipulator.instance().GetLocationID());

            //Add parameters to parameterBox
            list = FXCollections.observableArrayList(SaveManipulator.instance().GetParameters());
            parameterBox.setItems(list);
            //Set to choicebox
            if(SaveManipulator.instance().currentParameter != null) {
                parameterBox.getSelectionModel().select(SaveManipulator.instance().GetParameterID());
            }



        }
    }
    @FXML
    public void SetChoiceboxLocation() {
        if (SaveManipulator.instance().checkIfFileIsLoaded()) {
              selectedLocationId = locationBox.getSelectionModel().getSelectedIndex();
              SaveManipulator.instance().SetLocationFromID(selectedLocationId);
              if(SaveManipulator.instance().LocationIsMarketOrMountain()) {
                  Alert parameterAlert = new Alert(Alert.AlertType.WARNING);
                  parameterAlert.showAndWait();
              }
        }
    }
    @FXML
    public void SetChoiceboxParameter() {
        if (SaveManipulator.instance().checkIfFileIsLoaded()) {

        }
    }
    @FXML
    public void SetChoiceboxItem(){
        if(SaveManipulator.instance().checkIfFileIsLoaded()){
            selectedItemId = itemBox.getSelectionModel().getSelectedIndex();
            possesionCheckbox.setSelected(SaveManipulator.instance().itemList[selectedItemId]);
        }
    }
    @FXML
    public void SetCheckboxItem(){
        if(SaveManipulator.instance().checkIfFileIsLoaded()){
            SaveManipulator.instance().SetItem(selectedItemId,!SaveManipulator.instance().itemList[selectedItemId]);
        }
    }

    @FXML
    public void SetAllItems(){
        if (SaveManipulator.instance().checkIfFileIsLoaded()) {
            SaveManipulator.instance().SetAllItem(true);
            possesionCheckbox.setSelected(true);
        }
    }

    @FXML
    public void SetNoItems(){
        if (SaveManipulator.instance().checkIfFileIsLoaded()) {
            SaveManipulator.instance().SetAllItem(false);
            possesionCheckbox.setSelected(false);
        }
    }

    @FXML
    public void SetChoiceboxChar() {
        if (SaveManipulator.instance().checkIfFileIsLoaded()) {
            SaveManipulator.instance().SetCharacter(charBox.getSelectionModel().getSelectedIndex());
        }
    }


    @FXML
    public void SaveFile(ActionEvent event) throws IOException {
        SaveManipulator.instance().WriteFile();
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

}
