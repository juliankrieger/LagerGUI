package Controllers;

import Controllers.structs.Artikel;
import Controllers.structs.Lager;
import Controllers.views.ArtikelOverviewController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LagerApp");

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
