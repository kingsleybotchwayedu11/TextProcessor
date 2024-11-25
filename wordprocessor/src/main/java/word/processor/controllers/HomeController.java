package word.processor.controllers;

import java.io.File;
import java.nio.file.Path;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import word.processor.App;
import word.processor.utils.TextState;
import word.processor.utils.UndoAndRedo;

public class HomeController {
    StringProperty st = new SimpleStringProperty("new Simple String Property");

    @FXML
    private AnchorPane replaceHide;
    @FXML
    private MenuItem aboutMenu;

    @FXML
    private Text characterCount;

    @FXML
    private TextField find;

    @FXML
    private Button findButton;

    @FXML
    private Label findReport;

    @FXML
    private ChoiceBox<String> findType;

    @FXML
    private MenuItem newFile;

    @FXML
    private MenuItem openMenu;

    @FXML
    private Button redo;

    @FXML
    private TextField replace;

    @FXML
    private Button replaceButton;

    @FXML
    private ChoiceBox<String> replaceTyp;

    @FXML
    private MenuItem saveMenu;

    @FXML
    private TextArea textArea;

    @FXML
    private Button undo;

    @FXML
    private Text wordCount;
    
    @FXML
    private Label operationReport;

    @FXML
    private MenuItem saveAsMenu;

    
    @FXML
    private ChoiceBox<String> sensitiveCase;

    @FXML
    void aboutAction(ActionEvent event) {
        
    }

    @FXML
    void newFileAction(ActionEvent event) {
        //clear all the state
        textArea.setText("");
        TextState.clearState();
    }

    @FXML
    void openMenuAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(App.displayTage);
        if(selectedFile != null) {
            //read the file content as a string and pass it to the text area, after save the file path to the file and chose save to true;
            Boolean isRead = TextState.readFromFile(selectedFile.getAbsolutePath());
            if(isRead) {
                textArea.setText(TextState.getCurrentText());
            }
        }
    } 
    @FXML
    void redoAction(ActionEvent event) {
        boolean isrevered = UndoAndRedo.redoState();
        if(isrevered) {
         textArea.setText(TextState.getCurrentText());
        }
    }

    @FXML
    void undoAction(ActionEvent event) {
       boolean isUndone = UndoAndRedo.undoState();
       if(isUndone) {
        textArea.setText(TextState.getCurrentText());
       }
    }
    @FXML
    private void zoomInAction() {
        TextState.incrementFontSize(2);
        textArea.setFont(Font.font(TextState.getFontSize()));
    }
    @FXML
    private void  zoomOutAction() {
        TextState.incrementFontSize(-2);
        textArea.setFont(Font.font(TextState.getFontSize()));
    }
    @FXML
    void saveAction(ActionEvent event) {
        //if file is already saved write new changes to the existing file
        if(TextState.getIsSaved()) {
            operationReport.setText("Saved");
            operationReport.setVisible(true);
            return;
        }
            
        if(TextState.getFilePath() != null) {
            //save current changes to file
            Boolean saveChanges = TextState.saveTextToFile();
            if(saveChanges) {
                operationReport.setText("Saved Success");
            } else {
                operationReport.setText("Couldn't save");

            }
        } else {
            //show dialog
            FileChooser fileDialog = new FileChooser();
            fileDialog.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"));
            File savedPath = fileDialog.showSaveDialog(App.displayTage);
            if(savedPath != null) {
                TextState.setFilePath(Path.of(savedPath.getAbsolutePath()));
                Boolean isSaved = TextState.saveTextToFile();
                if(isSaved) {
                    operationReport.setText("Saved Success");
                } else {
                    operationReport.setText("Couldn't Save");

                }
            }

        }
        operationReport.setVisible(true);
    }

    @FXML
    void textChangeAction(InputMethodEvent event) {
    }

    @FXML
    void saveAsAction(ActionEvent event) {
        FileChooser fileDialog = new FileChooser();
            fileDialog.getExtensionFilters().addAll(
            new ExtensionFilter("Text Files", "*.txt"));
            File savedPath = fileDialog.showSaveDialog(App.displayTage);
            if(savedPath != null) {
                Boolean isSaved = TextState.saveTextToFile(Path.of(savedPath.getAbsolutePath()));
                if(isSaved) {
                    //show on screen else report
                    operationReport.setText("Saved");
                }
            }

        }
    
   
    @FXML
    void initialize() {
        findType.getItems().addAll("Text","Regex");
        findType.setValue("Text");
        replaceTyp.getItems().addAll("One","All");
        replaceTyp.setValue("One");
        sensitiveCase.getItems().addAll("Case","Ignore");
        sensitiveCase.setValue("Case");
        //set the font size of before window loads
        TextState.setFontSize(textArea.getFont().getSize());
        //setting unchage hanlder for textArea property
        textArea.textProperty().addListener((observable, oldValue, newValue) -> {
           //save changes to text
           TextState.setCurrentText(newValue);
           characterCount.setText(newValue.length() + "");
            //check if character at the last index is space save to stack;
            wordCount.setText(TextState.findNumberOfWords() + "");
            operationReport.setText(" ");
            
            //states are when new characters are entered
            UndoAndRedo.storeStates();
        });

        find.textProperty().addListener((observable, oldValue, newValue) -> {
            findReport.setText(" ");
            if(newValue.length() != 0)
                findButton.setDisable(false);
            else { 
                findButton.setDisable(true);
                replaceHide.setVisible(false);
            }
        });
        //change replace button enabling state
        replace.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.length() != 0)
                replaceButton.setDisable(false);
            else 
                replaceButton.setDisable(true);
        });
        //replace            
    }

    @FXML
    private void findAction() {
        if(TextState.getCurrentText() == null) {
            findReport.setText("No text in text area");
            findReport.setVisible(true);
            return;
           }
        int count = TextState.findNumberOfMatch(find.getText(), (sensitiveCase.getValue().equals("Ignore")) ? true : false);
        if(count < 0) {
            findReport.setText("Wrong Regex Syntax");
        } else {
            //check if no match found,
            if(count > 0) {
                replaceHide.setVisible(true);
            }
            findReport.setText(count + " matched");
        }
        findReport.setVisible(true);
    }
    
    @FXML 
    private void replaceAction() {
        Boolean isReplaced = TextState.replace(replace.getText(), (replaceTyp.getValue().equals("All")) ? true : false);
        if(isReplaced) {
            textArea.setText(TextState.getCurrentText());
            findReport.setText(TextState.findNumberOfMatch() + " ");
        }
    }
}
