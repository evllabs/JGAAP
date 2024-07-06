package com.jgaap.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.backend.Canonicizers;
import com.jgaap.generics.Canonicizer;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Canonicizer Tab Class.
 * This Class creates the scene for the Canonicizer Tab and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_CanTab {

    private static HBox bottomButtons;
    private static ListView<String> canList;
    private static ListView<String> selList;
    private static TextArea area;
    private static ArrayList<Canonicizer> CanonicizerMasterList;
    private static ArrayList<String> canonName;
    private static ArrayList<String> CanonicizerComboBoxModel;
    private static HashMap<String, Pair<Canonicizer, Object>> SelectedCanonicizerMap;
    private static List<Object> docTypesList;
    private static ComboBox<String> comboBox;
    private static Logger logger;
    private static API JAPI;
    private static GUI_NotesWindow noteBox;
    private VBox box;
    private ObservableList<String> canItems;
    private ObservableList<String> selItems;

    /**
     * Constructor for the class.
     */
    public GUI_CanTab() {
        comboBox = new ComboBox<String>();
        logger = Logger.getLogger(GUI_CanTab.class);
        docTypesList = new ArrayList<Object>();
        CanonicizerComboBoxModel = new ArrayList<String>();
        SelectedCanonicizerMap = new HashMap<String, Pair<Canonicizer, Object>>();
        box = new VBox();
        noteBox = new GUI_NotesWindow();
        JAPI = API.getInstance();
        init_canonicizers();
        logger.info("Finished building Canonicizer Tab");
    }

    /**
     * Builds the pane row by row.
     */
    private void build_pane() {
        logger.info("Building Canonicizer Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(bottomButtons);
    }

    /**
     * Method for building the 'Top Row' of GUI elements.
     * 
     * @return HBox
     */
    private HBox init_rowOne() {
        HBox box = new HBox(5);
        HBox selNotes = new HBox();
        VBox canBox = new VBox();
        VBox selBox = new VBox();
        Label can = new Label("Canonicizers");
        Label sel = new Label("Selected");
        Button notes = noteBox.getButton();
        Region region1 = new Region();

        HBox.setHgrow(region1, Priority.ALWAYS);
        // HBox.setHgrow(region2, Priority.ALWAYS);
        can.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        sel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        selNotes.getChildren().addAll(sel, region1, notes);
        canBox.getChildren().addAll(can, init_listLeft());
        selBox.getChildren().addAll(selNotes, init_listRight());

        box.getChildren().addAll(canBox, init_rowTwoButtons(), selBox);
        return box;
    }

    /**
     * Method for building the 'Second Row' of GUI elements.
     * 
     * @return VBox
     */
    private VBox init_rowTwo() {
        VBox box = new VBox(5);
        Label can = new Label("Canonicizer Description");
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
     * Method for generating the List Box of Canocinizers.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_listLeft() {
        canList = new ListView<String>();
        canonName = new ArrayList<String>();
        for (Canonicizer i : CanonicizerMasterList) {
            canonName.add(i.displayName());
        }
        this.canItems = FXCollections.observableArrayList(canonName);

        canList.setItems(this.canItems);
        canList.prefHeightProperty().bind(this.box.heightProperty());
        canList.prefWidthProperty().bind(this.box.widthProperty());
        canList.setOnMouseClicked(e -> {
            String sel = canList.getSelectionModel().getSelectedItem();
            Iterator<Canonicizer> iter = CanonicizerMasterList.iterator();
            while(iter.hasNext()){
                Canonicizer temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        return canList;
    }

    /**
     * Method for generating List Box of selected Canocinizers.
     * 
     * @return ListView<String>
     */
    private ListView<String> init_listRight(){
        selList = new ListView<String>();
        this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());

        selList.setItems(this.selItems);
        selList.prefHeightProperty().bind(this.box.heightProperty());
        selList.prefWidthProperty().bind(this.box.widthProperty());
        selList.setOnMouseClicked(e -> {
            String sel = selList.getSelectionModel().getSelectedItem();
            Iterator<Canonicizer> iter = CanonicizerMasterList.iterator();
            while(iter.hasNext()){
                Canonicizer temp = iter.next();
                if(sel.equalsIgnoreCase(temp.displayName())){
                    area.setText(temp.longDescription());
                }
            }
            e.consume();
        });

        return selList;
    }

    /**
     * Method for generating a VBox containing the buttons for de/selecting items
     * for the Selection Box.
     * 
     * @return VBox
     */
    private VBox init_rowTwoButtons() {
        VBox box = new VBox(5);
        Button left = new Button("<-");
        Button right = new Button("->");
        Button clear = new Button("Clear");
        Region region1 = new Region();
        Region region2 = new Region();
        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);
        left.setTooltip(new Tooltip("Remove"));
        right.setTooltip(new Tooltip("Add"));
        clear.setTooltip(new Tooltip("Remove all"));
        left.setOnAction(e -> {
            if(!SelectedCanonicizerMap.isEmpty()){
                Iterator<String> iter = SelectedCanonicizerMap.keySet().iterator();
                while (iter.hasNext()) {
                    String i = iter.next();
                    if(selList.getSelectionModel().getSelectedItem().equalsIgnoreCase(i)){
                        canonDeselected(i);
                    }
                }
                selList.refresh();
                canList.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
            for (Canonicizer i : CanonicizerMasterList) {
                if(canList.getSelectionModel().getSelectedItem().equalsIgnoreCase(i.displayName())){
                    canonSelected(i, comboBox.getSelectionModel().getSelectedItem());
                }
            }
            selList.refresh();
            canList.refresh();
            e.consume();
        });
        clear.setOnAction(e -> {
            if(!SelectedCanonicizerMap.isEmpty()){
                SelectedCanonicizerMap.clear();
                this.canItems = FXCollections.observableArrayList(canonName);
                this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());
                canList.setItems(this.canItems);
                selList.setItems(this.selItems);
                selList.refresh();
                canList.refresh();
            }
            e.consume();
        });

        box.getChildren().addAll(region1, init_rowTwoSelectionDropDown(), right, left, clear, region2);
        box.setAlignment(Pos.BASELINE_CENTER);

        return box;
    }

    /**
     * Method for creation of the document format selection box.
     * 
     * @return ComboBox<String>
     */
    private ComboBox<String> init_rowTwoSelectionDropDown() {
        UpdateCanonicizerDocTypeComboBox();
        comboBox.setMinSize(100, 25);
        comboBox.getSelectionModel().select(0);

        return comboBox;
    }
    /**
     * Method for building the Canonicizer Master List.
     */
    private void init_canonicizers() {
        CanonicizerMasterList = new ArrayList<Canonicizer>();
        for (int i = 0; i < Canonicizers.getCanonicizers().size(); i++) {
            Canonicizer canonicizer = Canonicizers.getCanonicizers().get(i);
            if (canonicizer.showInGUI())
                CanonicizerMasterList.add(canonicizer);
        }
    }
    /**
     * Method for Adding a Canonicizer.
     * 
     * @param method Canonicizer
     * @param doc Document or String
     */
    private void canonSelected(Canonicizer method, Object doc) {
        logger.info("Adding Canonicizer "+method.displayName());
        Pair<Canonicizer, Object> temp = new Pair<Canonicizer, Object>(method, doc);
        String key = temp.getFirst().displayName()+" ["+comboBox.getSelectionModel().getSelectedItem()+"]";
        SelectedCanonicizerMap.put(key, temp);

        this.selItems = FXCollections.observableList(SelectedCanonicizerMap.keySet().parallelStream().toList());
        canList.setItems(this.canItems);
        selList.setItems(this.selItems);
        selList.getSelectionModel().select(this.selItems.getLast());
    }
    /**
     * Method for removing a selected Canonicizer.
     * 
     * @param key String
     */
  private void canonDeselected(String key) {
        logger.info("Removing Canonicizer "+key);
        SelectedCanonicizerMap.remove(key);
        this.canItems = FXCollections.observableArrayList(canonName);
        this.selItems = FXCollections.observableArrayList(SelectedCanonicizerMap.keySet().parallelStream().toList());
        canList.setItems(this.canItems);
        selList.setItems(this.selItems);
    }
    /**
     * Method for updating the ComboBox Element for the Canonicizer.
     */
    public static void UpdateCanonicizerDocTypeComboBox() {
        logger.info("Updating Canonicizer ComboBox");
        comboBox.getItems().clear();
        CanonicizerComboBoxModel.clear();
        docTypesList.clear();
        docTypesList.add("All");
		docTypesList.add(Document.Type.GENERIC);
		docTypesList.add(Document.Type.DOC);
		docTypesList.add(Document.Type.PDF);
		docTypesList.add(Document.Type.HTML);
		for(Document document : JAPI.getDocuments()){
			docTypesList.add(document.getTitle());
		}
		for(Object obj : docTypesList){
			CanonicizerComboBoxModel.add(obj.toString());
		}
        comboBox.setItems(FXCollections.observableArrayList(CanonicizerComboBoxModel));
        comboBox.getSelectionModel().select(0);
	}

    /**
     * Returns the built Pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        logger.info("Finished building Canonicizer Tab");
        build_pane();
        return this.box;
    }
    /**
     * Sets the bottom row buttons.
     * 
     * @param box HBox
     */
    public void setBottomButtons(HBox box){
        bottomButtons = box;
    }
    /**
     * Gives the current selected Canonicizers.
     * 
     * @return HashMap<String,Pair<Canonicizer, Object>>
     */
    public static HashMap<String,Pair<Canonicizer, Object>> getSelectedCanList(){
        return SelectedCanonicizerMap;
    }

}
