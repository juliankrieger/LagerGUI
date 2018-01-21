package views.Controllers;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;

public class ArtikelNewController extends ArtikelDialogueController {

    @FXML
    private Button button;

    //TODO handleOk on enter button pressed
    @FXML
    private void initialize() {
        /*button.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCharacter().equals("\n")){
                    System.out.println("omegalul");
                }

            }
        });*/
    }

    @FXML
    private void handleKeyPress(){

    }
}
