package com.jgaap.GUI;
import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Main Window Class.
 * This Class is the heart of the GUI, and controls the GUI as a whole. It is used to intertwine all the other classes.
 * 
 * @author Edward Polens
 */
public class GUI_MainWindow extends Application{

    private static Logger logger;
    private static BorderPane pane;
    private static TabPane tabPane;

    /**
     * Used for debugging.
     * 
     * @param args Normal Java Main Args
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Main Method for setting up the GUI for JavaFX.
     */
    public void start(Stage mainStage) {
        logger = Logger.getLogger(GUI_MainWindow.class);
        mainStage.setOnCloseRequest(e -> {
            e.consume();
            Platform.exit();
            System.exit(0);
        });
        mainStage.setTitle(JGAAPConstants.VERSION+" BETA");
        mainStage.getIcons().add(new Image("/ui/jgaap-icon.png"));
        mainStage.setScene(init_mainScene(mainStage));
        mainStage.minHeightProperty().set(600);
        mainStage.minWidthProperty().set(1000);
        mainStage.show();
    }
    /**
     * Method for constructing the full GUI for JGAAP.
     * 
     * @return Scene
     */
    private Scene init_mainScene(Stage stage){
        logger.info("Building Main Window");
        tabPane = new TabPane();
        Scene scene;
        VBox docPane, canPane, edPane, ecPane, anPane,reviewPane;

        MenuBar bar = init_MenuBar(stage);
        Tab doc = new Tab("Documents");
        Tab canon = new Tab("Canonicizers");
        Tab eve = new Tab("Event Driver");
        Tab evecul = new Tab("Event Culling");
        Tab anMeth = new Tab("Analysis Methods");
        Tab review = new Tab("Review & Process");
        
        doc.setId("Documents");
        canon.setId("Canonicizers");
        eve.setId("Event Driver");
        evecul.setId("Event Culling");
        anMeth.setId("Analysis Methods");
        review.setId("Review & Process");

        doc.setClosable(false);
        canon.setClosable(false);
        eve.setClosable(false);
        evecul.setClosable(false);
        anMeth.setClosable(false);
        review.setClosable(false);

        pane = new BorderPane();
        
        tabPane.prefHeightProperty().bind(pane.heightProperty());
        tabPane.prefWidthProperty().bind(pane.widthProperty());
        GUI_DocTab docTab = new GUI_DocTab(stage);
        GUI_CanTab canTab = new GUI_CanTab();
        GUI_EDTab edTab = new GUI_EDTab();
        GUI_ECTab ecTab = new GUI_ECTab();
        GUI_AnalysisTab anTab = new GUI_AnalysisTab();
        GUI_ReviewTab reviewTab = new GUI_ReviewTab();

        docTab.setBottomButtons(init_bottomButtons());
        canTab.setBottomButtons(init_bottomButtons());
        edTab.setBottomButtons(init_bottomButtons());
        ecTab.setBottomButtons(init_bottomButtons());
        anTab.setBottomButtons(init_bottomButtons());


        docPane = docTab.getPane();
        canPane = canTab.getPane();
        edPane = edTab.getPane();
        ecPane = ecTab.getPane();
        anPane = anTab.getPane();
        reviewPane = reviewTab.getPane();
        docPane.setPadding(new Insets(5));
        canPane.setPadding(new Insets(5));
        edPane.setPadding(new Insets(5));
        ecPane.setPadding(new Insets(5));
        anPane.setPadding(new Insets(5));
        reviewPane.setPadding(new Insets(5));
        docPane.prefHeightProperty().bind(tabPane.heightProperty());
        docPane.prefWidthProperty().bind(tabPane.widthProperty());
        canPane.prefHeightProperty().bind(tabPane.heightProperty());
        canPane.prefWidthProperty().bind(tabPane.widthProperty());
        edPane.prefHeightProperty().bind(tabPane.heightProperty());
        edPane.prefWidthProperty().bind(tabPane.widthProperty());
        ecPane.prefHeightProperty().bind(tabPane.heightProperty());
        ecPane.prefWidthProperty().bind(tabPane.widthProperty());
        anPane.prefHeightProperty().bind(tabPane.heightProperty());
        anPane.prefWidthProperty().bind(tabPane.widthProperty());
        reviewPane.prefHeightProperty().bind(tabPane.heightProperty());
        reviewPane.prefWidthProperty().bind(tabPane.widthProperty());
        doc.setContent(docPane);
        canon.setContent(canPane);
        eve.setContent(edPane);
        evecul.setContent(ecPane);
        anMeth.setContent(anPane);
        review.setContent(reviewPane);
        

        tabPane.getTabs().add(doc);
        tabPane.getTabs().add(canon);
        tabPane.getTabs().add(eve);
        tabPane.getTabs().add(evecul);
        tabPane.getTabs().add(anMeth);
        tabPane.getTabs().add(review);

        tabPane.getSelectionModel().selectedItemProperty().addListener(e -> {
            if(tabPane.getSelectionModel().getSelectedItem().getId().equalsIgnoreCase("Review & Process")){
                reviewTab.refreshView();
            }
        });


        pane.setTop(bar);
        pane.setCenter(tabPane);

        scene = new Scene(pane, 1000, 600);


        return scene;
    }
    /**
     * Builds the Menu Bar and its containing items/functions.
     * 
     * @return MenuBar
     */
     private MenuBar init_MenuBar(Stage stage) {
        GUI_MenuItemsBatch items = new GUI_MenuItemsBatch(stage);
       // GUI_LogWindow log = new GUI_LogWindow();
        MenuBar bar = new MenuBar();
        Menu file = new Menu("File");
        Menu help = new Menu("Help");
        Menu windows = new Menu("Window");
        Menu batch = new Menu("Batch Documents");
        Menu aaac = new Menu("AAAC Problems");
        MenuItem quit = new MenuItem("Quit");
        MenuItem about = new MenuItem("About");
        MenuItem show = new MenuItem("Show Results Window");
        MenuItem console = new MenuItem("Show JGAAP Logging Window");

        about.setOnAction(e -> {
            e.consume();
            GUI_JGAAPAboutWindow.show();
        });
        quit.setOnAction(e -> {
            e.consume();
            System.exit(0);
        });
        show.setOnAction(e -> {
            e.consume();
            GUI_ResultsWindow.showStage();
        });
        console.setOnAction(e -> {
            e.consume();
            //log.showStage();
        });
        
        aaac.getItems().addAll(items.getProblems());
        batch.getItems().addAll(items.getItems());
        file.getItems().addAll(batch,aaac,quit);
        help.getItems().add(about);
        windows.getItems().addAll(show,console);
        bar.getMenus().add(file);
        bar.getMenus().add(windows);
        bar.getMenus().add(help);

        return bar;
     }
     /**
      * Builds the bottom buttons that control the TabView in the main window.

      * @return  HBox
      */
    private HBox init_bottomButtons() {
        HBox box = new HBox(5);
        Button finish = new Button("Finish & Review");
        Button next = new Button("Next");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        next.setTooltip(new Tooltip("Go to the next Tab"));
        finish.setTooltip(new Tooltip("Go to the Review Tab"));
        finish.setOnAction(e -> {
            tabPane.getSelectionModel().selectLast();
            e.consume();
        });
        next.setOnAction(e -> {
            tabPane.getSelectionModel().selectNext();
            e.consume();
        });
        box.getChildren().add(region1);
        box.getChildren().add(finish);
        box.getChildren().add(next);
        box.setSpacing(10);
        return box;
    }
}