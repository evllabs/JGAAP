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
import com.jgaap.generics.EventCuller;
import com.jgaap.backend.EventCullers;
import com.jgaap.backend.API;

/**
 * Event Culling Tab Class.
 * This Class creates the scene for the Event CUlling Tab and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_ECTab {

    private VBox paraBox, paraBoxChild, box;
    private static ObservableList<EventCuller> eventCullers;
    private static ObservableList<EventCuller> selected;
    private static ArrayList<EventCuller> EventCullersMasterList;
    private static ListView<EventCuller> listLeft;
    private static ListView<EventCuller> listRight;
    private static HBox bottomButtons;
    private static TextArea area;
    private static Logger logger;
    private static API JAPI;
    private static GUI_NotesWindow noteBox;

    /**
     * Constructor for the class.
     */
    public GUI_ECTab() {
        logger = Logger.getLogger(GUI_ECTab.class);
        JAPI = API.getInstance();
        init_eventCullers();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
    }

    /**
     * Method for building the Window row by row.
     */
    private void build_pane() {
        logger.info("Building Event Culler Tab");
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
        Label can = new Label("Event Culling");
        Label sel = new Label("Selected");
        Label para = new Label("Parameters");
        Button notes = noteBox.getButton();
        HBox box = new HBox(5);
        HBox notesBox = new HBox();
        VBox edBox = new VBox();
        VBox selBox = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        this.paraBox = new VBox();
        this.paraBoxChild = new VBox();
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
     * @return VBox
     */
    private VBox init_rowTwo() {
        VBox box = new VBox(5);
        Label can = new Label("Event Culling Description");
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
     * Method for building the Event Culling Selection Box.
     * 
     * @return ListView<EventCuller>
     */
    private ListView<EventCuller> init_ListLeft() {
        listLeft = new ListView<EventCuller>();
        eventCullers = FXCollections.observableArrayList(EventCullersMasterList);

        listLeft.setItems(eventCullers);
        listLeft.setOnMouseClicked(e -> {
            area.setText(listLeft.getSelectionModel().getSelectedItem().longDescription());
            e.consume();
        });
        listLeft.setCellFactory(param -> new ListCell<EventCuller>() {
            @Override
            protected void updateItem(EventCuller item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.displayName().equalsIgnoreCase("")) {
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        listLeft.prefHeightProperty().bind(this.box.heightProperty());
        listLeft.prefWidthProperty().bind(this.box.widthProperty());

        return listLeft;
    }

    /**
     * Method for showing the Selected Event Culling box.
     * 
     * @return ListView<EventCuller>
     */
    private ListView<EventCuller> init_ListRight() {
        listRight = new ListView<EventCuller>();
        selected = FXCollections.observableArrayList(JAPI.getEventCullers());
        listRight.setItems(selected);
        listRight.setCellFactory(param -> new ListCell<EventCuller>() {
            @Override
            protected void updateItem(EventCuller item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.displayName().equalsIgnoreCase("")) {
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        listRight.setOnMouseClicked(e -> {
            EventCuller sel = listRight.getSelectionModel().getSelectedItem();
            VBox para = sel.getNewGUILayout();
            para.prefHeightProperty().bind(this.box.heightProperty());
            para.prefWidthProperty().bind(this.box.widthProperty());
            this.paraBox.getChildren().removeLast();
            this.paraBox.getChildren().add(para);
            area.setText(sel.longDescription());
            e.consume();
        });

        listRight.prefHeightProperty().bind(this.box.heightProperty());
        listRight.prefWidthProperty().bind(this.box.widthProperty());

        return listRight;
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
            if (!JAPI.getEventCullers().isEmpty()) {
                ecDeselected(listRight.getSelectionModel().getSelectedItem());
                listRight.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
            ecSelected(listLeft.getSelectionModel().getSelectedItem());
            listRight.refresh();
            listRight.getSelectionModel().select(selected.getLast());
            e.consume();
        });
        all.setOnAction(e -> {
            allSelected();
            listRight.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            if (!JAPI.getEventDrivers().isEmpty()) {
                clearSelected();
                listRight.refresh();
            }
            e.consume();
        });

        box.getChildren().addAll(region1, right, left, all, clear, region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;
    }

    /**
     * Method for initializing the Event Culler Master list.
     */
    private void init_eventCullers() {
        EventCullersMasterList = new ArrayList<EventCuller>();
        for (int i = 0; i < EventCullers.getEventCullers().size(); i++) {
            EventCuller eventCuller = EventCullers.getEventCullers().get(i);
            if (eventCuller.showInGUI()) {
                EventCullersMasterList.add(eventCuller);
            }
        }
    }

    /**
     * Method for adding a selected Event Culler.
     * 
     * @param method String
     */
    private void ecSelected(EventCuller obj) {
        String method = obj.displayName();
        logger.info("Adding Event Culler " + method);
        try {
            JAPI.addEventCuller(method);
        } catch (Exception e) {
            logger.error(e.getCause(), e);
            e.printStackTrace();
        }
        selected = FXCollections.observableArrayList(JAPI.getEventCullers());
        listRight.setItems(selected);
    }

    /**
     * Method for adding all Event Cullers
     */
    private void allSelected() {
        logger.info("Adding all Event Cullers");
        for (EventCuller i : EventCullersMasterList) {
            try {
                JAPI.addEventCuller(i.displayName());
            } catch (Exception e) {
                logger.error(e.getCause(), e);
                e.printStackTrace();
            }
        }
        selected = FXCollections.observableArrayList(JAPI.getEventCullers());
        listRight.setItems(selected);
    }

    /**
     * Method to delete all selected Event Cullers.
     */
    private void clearSelected() {
        logger.info("Removing all selected Event Cullers");
        JAPI.removeAllEventCullers();
        selected = FXCollections.observableArrayList(JAPI.getEventCullers());
        listRight.setItems(selected);
    }

    /**
     * Method for removing a selected Event Culler.
     * 
     * @param method String
     */
    private void ecDeselected(EventCuller obj) {
        String method = obj.displayName();
        logger.info("Removing Event Culler " + method);
        JAPI.removeEventCuller(obj);
        selected = FXCollections.observableArrayList(JAPI.getEventCullers());
        listRight.setItems(selected);
    }

    /**
     * Getter for getting the built Pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        logger.info("Finished building Event Culler Tab");
        build_pane();
        return this.box;
    }

    /**
     * Method for applying the bottom buttons to the panel.
     * 
     * @param box HBox
     */
    public void setBottomButtons(HBox box) {
        bottomButtons = box;
    }

}
