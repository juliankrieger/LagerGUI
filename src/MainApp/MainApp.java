package MainApp;

import javafx.scene.control.Alert;
import structs.Artikel;
import structs.ArtikelListWrapper;
import structs.Lager;
import Logger.AppLogger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.prefs.Preferences;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.WindowEvent;
import views.Controllers.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * The Main App of the program
 */
public class MainApp extends Application {

    /**
     * The primary Stage, e.g the Most forefront window you see when you open the application
     */
    private Stage primaryStage;
    /***
     * The rootLayout, e.g the File Edit About tab you see on the top.
     */
    private BorderPane rootLayout;
    /**
     * The Lager, in which all Artikels are stored in an observable list
     */
    private Lager lager;


    /***
     * Basic Constructor, initializes the Lager
     */
    public MainApp(){
        this.lager = new Lager();

    }

    /**
     * Shows the Artikel overview inside the root layout.
     * This contains the split grid, whose left side is a Table View with three columns, and the right side showing
     * the details of the currently selected Artikel.
     */
    public void showArtikelOverview(){
        try{
            FXMLLoader loader = new FXMLLoader(); //creating a loader in which we will load our FXML file into
            loader.setLocation(MainApp.class.getResource("../views/FXML/ArtikelOverview.fxml")); //Setting the fxml file
            AnchorPane artikelOverview = (AnchorPane) loader.load(); //And casting loader.load into an Anchor Pane

            //set artikel overview into center of root layout
            rootLayout.setCenter(artikelOverview);

            //Give controller access to mainApp
            ArtikelOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }catch(IOException e){ //Log any exception into the logger, and print its stack trace.
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE,"Erorr while opening base application window" ,e);
        }
    }

    /**
     * Show the new Artikel Chooser Dialog, which allows the User to choose the Kind of Artikel he wants to create.
     * Currently , he can choose between Artikel, Buch, CD and DVD
     * TODO Add more.
     * @return The Type the User has chosen.
     */
    public Artikel showArtikelNewChooserDialog(){
        try {

            //Load the fxml file and create a new Stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../views/FXML/ArtikelNewChooser.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            //create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Choose Artikel Type");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);


            //Set controller
            ArtikelNewChooserController controller = loader.getController();
            controller.setDialogStage(dialogStage);


            //show dialog and wait until user closes it
            dialogStage.showAndWait();

            //If OK is clicked, return the Artikel
            if(controller.isOkClicked()){
                return controller.getArt();
            }
        }catch(IOException e){ //Log Exceptions
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal error while choosing Kind of a new Artikel", e);

        }
        return  null; //If the user pressed cancel.
    }

    /**
     * Show the new Artikel Dialog, in which the User can create a new Artikel based on what Choice he made before
     * @param art The at this point empty Artikel we want to assign Values to
     * @return true if everything worked, false else.
     */
    public boolean showArtikelNewDialog(Artikel art){
        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../views/FXML/ArtikelNewDialogue.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("New Artikel");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the artikel into the controller.
            ArtikelNewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArtikel(art);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){ //Log exceptions
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal error while editing an Artikel", e);
            return false;
        }
    }

    /**
     * Show the Edit dialog, in which the User can edit any Artikel
     * @param art The Artikel we want to edit
     * @return true if it worked, false on else.
     */
    public boolean showArtikelEditDialog(Artikel art){
        try{
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../views/FXML/ArtikelEditDialogue.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Artikel");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the artikel into the controller.
            ArtikelEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setArtikel(art);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        }catch(IOException e){//log exceptions
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal error while editing an Artikel", e);
            return false;
        }
    }

    /**
     * Start the primary Stage, e.g the most forefront window.
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("LagerApp");
        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() { //Close the FileOutputStream in AppLogger on exit
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
            loader.setLocation(MainApp.class.getResource("../views/FXML/Root.fxml"));
            rootLayout = (BorderPane) loader.load();


            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootController controller = loader.getController();
            controller.setMainApp(this);
            primaryStage.show();


        } catch (IOException e) {
            e.printStackTrace();
            AppLogger.logger.log(Level.SEVERE, "Fatal Error while trying to initialize Root layout(File bar)", e);
        }

        // Try to load last opened person file.
        File file = getArtikelFilePath();
        if (file != null) {
            loadArtikelDataFromFile(file);
        }
    }


    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Main, launches the App
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the lager
     * @return
     */
    public Lager getLager(){
        return this.lager;
    }


    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getArtikelFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setArtikelFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("LagerGUI - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("LagerGUI");
        }
    }



    /**
     * Loads person data from the specified file. The current person data will
     * be replaced.
     *
     * @param file
     */
    public void loadArtikelDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ArtikelListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ArtikelListWrapper wrapper = (ArtikelListWrapper) um.unmarshal(file);

            this.lager.getArtikelData().clear();
            this.lager.getArtikelData().addAll(wrapper.getArtikelList());

            // Save the file path to the registry.
            setArtikelFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Saves the current person data to the specified file.
     *
     * @param file
     */
    public void saveArtikelDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ArtikelListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our person data.
            ArtikelListWrapper wrapper = new ArtikelListWrapper();
            wrapper.setArtikelList(this.lager.getArtikelData());

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setArtikelFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }


}
