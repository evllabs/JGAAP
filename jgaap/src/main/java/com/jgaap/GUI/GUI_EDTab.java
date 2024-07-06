package com.jgaap.GUI;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import com.jgaap.generics.EventDriver;
import com.jgaap.backend.EventDrivers;
import com.jgaap.backend.API;

/**
 * Event Driver Tab Class.
 * This Class creates the scene for the Event Driver Tab and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_EDTab {

    private VBox box, paraBoxChild, paraBox;
    private static ArrayList<EventDriver> EventDriverMasterList;
    private static ObservableList<EventDriver> selected;
    private static ObservableList<EventDriver> eventDrivers;
    private static ListView<EventDriver> edList;
    private static ListView<EventDriver> selList;
    private static TextArea area;
    private static HBox bottomButtons, notesBox;
    private static API JAPI;
    private static Logger logger;
    private static GUI_NotesWindow noteBox;

    /**
     * Constructor for the class.
     */
    public GUI_EDTab() {
        logger = Logger.getLogger(GUI_EDTab.class);
        JAPI = API.getInstance();
        init_EventDrivers();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
    }

    /**
     * Method for building the Window row by row.
     */
    private void build_pane() {
        logger.info("Building Event Driver Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(bottomButtons);
    }

    /**
     * Method for building the 'Top Level' of GUI Elements.
     * 
     * @return HBox
     */
    private HBox init_rowOne() {
        Label can = new Label("Event Drivers");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = noteBox.getButton();
        HBox box = new HBox(5);
        VBox edBox = new VBox();
        VBox selBox = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        notesBox = new HBox();
        paraBox = new VBox();
        paraBoxChild = new VBox();
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        para.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        paraBoxChild.setStyle("-fx-border-color: black");
        paraBoxChild.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChild.prefWidthProperty().bind(this.box.widthProperty());

        notesBox.getChildren().addAll(para, region1, notes);

        edBox.getChildren().addAll(can, init_ListLeft());
        selBox.getChildren().addAll(sel, init_ListRight());
        paraBox.getChildren().addAll(notesBox, paraBoxChild);

        box.getChildren().addAll(edBox, init_rowTwoButtons(), selBox, paraBox);

        return box;
    }

    /**
     * Method for building the 'Second level' of GUI elements.
     * 
     * @return
     */
    private VBox init_rowTwo() {
        VBox box = new VBox(5);
        Label can = new Label("Event Driver Description");
        area = new TextArea();

        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        area.setText("");
        area.setWrapText(true);
        area.setEditable(false);
        area.prefHeightProperty().bind(this.box.heightProperty());
        area.prefWidthProperty().bind(this.box.widthProperty());
        box.getChildren().add(can);
        box.getChildren().add(area);

        return box;

    }

    /**
     * Method for building the Event Driver Selection Box.
     * 
     * @return ListView<String>
     */
    private ListView<EventDriver> init_ListLeft() {
        edList = new ListView<EventDriver>();
        eventDrivers = FXCollections.observableArrayList(EventDriverMasterList);

        edList.setItems(eventDrivers);
        edList.setOnMouseClicked(e -> {
            area.setText(edList.getSelectionModel().getSelectedItem().longDescription());
            e.consume();
        });
        edList.setCellFactory(param -> new ListCell<EventDriver>(){
            @Override
            protected void updateItem(EventDriver item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null || item.displayName().equalsIgnoreCase("")){
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        edList.prefHeightProperty().bind(this.box.heightProperty());
        edList.prefWidthProperty().bind(this.box.widthProperty());

        return edList;
    }

    /**
     * Method for showing the Selected Driver Culling box.
     * 
     * @return ListView<String>
     */
    private ListView<EventDriver> init_ListRight() {
        selList = new ListView<EventDriver>();
        selected = FXCollections.observableArrayList(JAPI.getEventDrivers());
        selList.setItems(selected);
        selList.setCellFactory(param -> new ListCell<EventDriver>(){
            @Override
            protected void updateItem(EventDriver item, boolean empty){
                super.updateItem(item, empty);
                if(empty || item == null || item.displayName().equalsIgnoreCase("")){
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        selList.setOnMouseClicked(e -> {
            EventDriver sel = selList.getSelectionModel().getSelectedItem();
            VBox para = sel.getNewGUILayout();
            para.prefHeightProperty().bind(this.box.heightProperty());
            para.prefWidthProperty().bind(this.box.widthProperty());
            this.paraBox.getChildren().removeLast();
            this.paraBox.getChildren().add(para);
            area.setText(sel.longDescription());
            e.consume();
        });

        selList.prefHeightProperty().bind(this.box.heightProperty());
        selList.prefWidthProperty().bind(this.box.widthProperty());

        return selList;
    }

    /**
     * Method for generating the selection box buttons.
     * 
     * @return VBox
     */
    private VBox init_rowTwoButtons() {
        VBox box = new VBox(5);
        Region region1 = new Region();
        Region region2 = new Region();
        Button left = new Button("<-");
        Button right = new Button("->");
        Button clear = new Button("Clear");
        Button all = new Button("All");
        left.setTooltip(new Tooltip("Remove"));
        right.setTooltip(new Tooltip("Add"));
        clear.setTooltip(new Tooltip("Remove all"));
        all.setTooltip(new Tooltip("Select all"));

        box.setMinSize(50, 0);

        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);

        left.setOnAction(e -> {
            if(!JAPI.getEventDrivers().isEmpty()){
                edDeselected(selList.getSelectionModel().getSelectedItem());
                selList.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
                edSelected(edList.getSelectionModel().getSelectedItem());
                selList.refresh();
                selList.getSelectionModel().select(selected.getLast());
            e.consume();
        });
        all.setOnAction(e -> {
            allSelected();
            selList.refresh();
            edList.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            if(!JAPI.getEventDrivers().isEmpty()){
                clearSelected();
                selList.refresh();
            }
            e.consume();
        });

        box.getChildren().addAll(region1, right, left, all, clear, region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }
    /**
     * Method for initializing the Event Driver Master List.
     */
    private void init_EventDrivers() {
        EventDriverMasterList = new ArrayList<EventDriver>();
        for (int i = 0; i < EventDrivers.getEventDrivers().size(); i++) {
            EventDriver eventDriver = EventDrivers.getEventDrivers().get(i);
            if (eventDriver.showInGUI())
                EventDriverMasterList.add(eventDriver);
        }
    }
    /**
     * Method for adding a selected Event Driver.
     * 
     * @param method EventDriver
     */
    private void edSelected(EventDriver obj) {
        String method = obj.displayName();
        logger.info("Adding Event Driver "+method);
        try {
            JAPI.addEventDriver(method);
        } catch (Exception e) {
            logger.error(e.getCause(), e);
            e.printStackTrace();
        }
        selected = FXCollections.observableArrayList(JAPI.getEventDrivers());
        selList.setItems(selected);
    }
    /**
     * Method for selecting all Event Drivers
     */
    private void allSelected() {
        logger.info("Adding all Event Drivers");
        for(EventDriver temp : EventDriverMasterList){
            try {
                JAPI.addEventDriver(temp.displayName());
            } catch (Exception e) {
                logger.error(e.getCause(), e);
                e.printStackTrace();
            }
        }
        selected = FXCollections.observableArrayList(JAPI.getEventDrivers());
        selList.setItems(selected);
    }
    /**
     * Method to delete all selected Event Drivers.
     */
    private void clearSelected(){
        logger.info("Removing all selected Event Drivers");
        JAPI.removeAllEventDrivers();
        selected = FXCollections.observableArrayList(JAPI.getEventDrivers());
        selList.setItems(selected);
        //this.paraBox.getChildren().removeLast();
        //this.paraBox.getChildren().add(this.paraBoxChild);
    }
    /**
     * Method for removing selected Event Driver.
     * 
     * @param method EventDriver
     */
    private void edDeselected(EventDriver obj) {
        String method = obj.displayName();
        logger.info("Removing Event Driver "+method);
        JAPI.removeEventDriver(obj);
        selected = FXCollections.observableArrayList(JAPI.getEventDrivers());
        selList.setItems(selected);
    }

    /**
     * Getter for getting the built Pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        build_pane();
        logger.info("Finished building Event Culler Tab");
        return this.box;
    }
    /**
     * Method for applying the bottom buttons to the panel.
     * 
     * @param box HBOX
     */
    public void setBottomButtons(HBox box) {
        bottomButtons = box;
    }

}
