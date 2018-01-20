package Controllers;

import Controllers.structs.Artikel;
import Controllers.structs.Lager;
import Controllers.views.ArtikelEditController;
import Controllers.views.ArtikelOverviewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.logging.Handler;
import java.util.logging.Level;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import sun.applet.Main;


public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Lager lager;

    public MainApp(){
        this.lager = new Lager();
        lager.addArtikel(new Artikel(1000, "Ding", 5 , BigDecimal.valueOf(10)));
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showArtikelOverview(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ArtikelOverview.fxml"));
            AnchorPane artikelOverview = (AnchorPane) loader.load();

            //set artikel overview into center of root layout
            rootLayout.setCenter(artikelOverview);

            //Give controller access to mainApp
            ArtikelOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }catch(IOException e){
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE,"Erorr while opening base application window" ,e);
        }
    }

    public boolean showArtikelEditDialog(Artikel art){
        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/ArtikelEditDialogue.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Artikel");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ArtikelEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArtikel(art);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal error while editing an Artikel", e);
            return false;
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LagerApp");
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                for(Handler handler : AppLogger.logger.getHandlers()){
                    handler.close();
                }
            }
        });

        initRootLayout();

        showArtikelOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("views/Root.fxml"));
            rootLayout = (BorderPane) loader.load();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal Error while trying to initialize Root layout(File bar)", e);
        }
    }


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Lager getLager(){
        return this.lager;
    }

}
