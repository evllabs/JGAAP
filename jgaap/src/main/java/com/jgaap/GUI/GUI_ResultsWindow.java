package com.jgaap.GUI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;

/**
 * GUI_ResultsWindow class is the class responsible for Building and Managing the window that displays the results.
 * 
 * @author Edward Polens
 */
public class GUI_ResultsWindow {

    private static Stage stage;
    private static TabPane tabs;
    private static ArrayList<String> resultStorage;
    private static Logger logger;

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    /**
     * Constructor for the class.
     */
    public GUI_ResultsWindow(){
        logger = Logger.getLogger(GUI_ResultsWindow.class);
        resultStorage = new ArrayList<String>();
        stage = new Stage();
        stage.setTitle("Results Window");
        stage.setScene(build_scene());

    }
    /**
     * Method builds the Scene for the Stage.
     * @return Scene
     */
    private Scene build_scene(){
        Scene scene;
        VBox box = new VBox(5);
        tabs = new TabPane();
        VBox.setVgrow(tabs, Priority.ALWAYS);
        tabs.prefHeightProperty().bind(box.prefHeightProperty());
        tabs.prefWidthProperty().bind(box.prefWidthProperty());
        box.getChildren().addAll(tabs);
        box.setMinSize(stage.getHeight(), stage.getWidth());

        scene = new Scene(box, 700, 900);
        return scene;
    }
    /**
     * Method builds the clear button at the bottom of the window.
     * @return HBox
     */
    private HBox init_button(){
        HBox buttonBox = new HBox(5);
        Region region1 = new Region();
        Button clear = new Button("Clear");
        HBox.setHgrow(region1, Priority.ALWAYS);
        clear.setTooltip(new Tooltip("Remove all results."));
        clear.setOnAction(e -> {
            resultStorage.clear();
            tabs.getTabs().clear();
            stage.close();
            e.consume();
        });
        buttonBox.getChildren().addAll(region1,clear);
        buttonBox.autosize();
        return buttonBox;
    }
    /**
     * Builds the TabPanel that holds all the child tabs from each result.
     * @param results String
     */
    public void build_resultTab(String results){
        String now = now();
        VBox box = build_tab(results);
        Tab resultTab = new Tab(now);
        logger.info("Adding "+now);
        resultStorage.add(results);
        box.prefHeightProperty().bind(tabs.heightProperty());
        box.prefWidthProperty().bind(tabs.widthProperty());
        box.setPadding(new Insets(5));
        resultTab.setContent(box);

        tabs.getTabs().add(resultTab);
        
    }
    /**
     * Builds a new tab for each new experiment ran.
     * @param results String
     */
    private VBox build_tab(String results){
        TextArea area = new TextArea();
        VBox box = new VBox(5);
        area.wrapTextProperty().set(true);
        area.setText(results);
        area.prefHeightProperty().bind(box.heightProperty());
        area.prefWidthProperty().bind(box.widthProperty());
        box.getChildren().addAll(area, init_button());
        return box;
    }
    /**
     * Gets the current date/time.
     * @return String
     */
    public static String now(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }
    /**
     * Show the window.
     */
    public static void showStage(){
        stage.show();
    }
    /**
     * Close (Hide) the Window.
     */
    public static void hideStage(){
        stage.hide();
    }
    
}
