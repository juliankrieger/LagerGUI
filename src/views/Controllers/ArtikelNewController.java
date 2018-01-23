package views.Controllers;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

public class ArtikelNewController extends ArtikelDialogueController {


    @FXML
    private void initialize() {



        okButton.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)){
                    System.out.println("ENTER PRESSED");
                    handleOk();
                }
            }
        });

    }

    @FXML
    private void handleKeyPress(){

    }
}
