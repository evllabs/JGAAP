package com.jgaap.GUI;

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
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.backend.AnalysisDrivers;
import com.jgaap.backend.DistanceFunctions;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.NonDistanceDependentAnalysisDriver;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;

/**
 * Analysis Tab Class.
 * This Class creates the scene for the Analysis Tab and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_AnalysisTab {

    private static ArrayList<AnalysisDriver> AnalysisDriverMasterList;
    private static ArrayList<DistanceFunction> DistanceFunctionsMasterList;
    private static TextArea anArea;
    private static TextArea dfArea;
    private static API JAPI;
    private static Logger logger;
    private static HBox bottomButtons;
    private static ListView<AnalysisDriver> anList;
    private static ListView<DistanceFunction> dfList;
    private static ListView<AnalysisDriver> selList;
    private static GUI_NotesWindow notesBox;
    private static ObservableList<AnalysisDriver> selItems;
    private VBox box, param, paraBoxChildOne;

    /**
     * Constructor for the class.
     */
    public GUI_AnalysisTab() {
        logger = Logger.getLogger(GUI_AnalysisTab.class);
        JAPI = API.getInstance();
        notesBox = new GUI_NotesWindow();
        this.box = new VBox();
        init_analysisDrivers();
        init_distanceFunctions();
    }

    /**
     * Builds the Pane row by row.
     */
    private void build_pane() {
        logger.info("Building Analysis Tab");
        this.box.getChildren().add(init_rowOne());
        this.box.getChildren().add(init_rowTwo());
        this.box.getChildren().add(bottomButtons);
    }

    /**
     * Creates the 'Top Row' of GUI elements in the window.
     * 
     * @return HBox
     */
    private HBox init_rowOne() {
        HBox box = new HBox(5);
        HBox noteBox = new HBox();
        VBox meth = new VBox();
        VBox sel = new VBox();
        this.param = new VBox();
        this.paraBoxChildOne = new VBox();
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        Button notes = notesBox.getButton();
        Label an = new Label("Analysis Method");
        Label df = new Label("Distance Function");
        Label se = new Label("Selected");
        Label am = new Label("Parameters");

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        se.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        am.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        paraBoxChildOne.setStyle("-fx-border-color: black"); 

        paraBoxChildOne.prefHeightProperty().bind(this.box.heightProperty());
        paraBoxChildOne.prefWidthProperty().bind(this.box.widthProperty());

        noteBox.getChildren().addAll(am, region1, notes);
        meth.getChildren().addAll(an, init_AnalysisMethodBox(), df, init_DistanceFunctionBox());
        sel.getChildren().addAll(se, init_SelectedBox());
        param.getChildren().addAll(noteBox, paraBoxChildOne);

        box.getChildren().addAll(meth, init_rowOneButtons(), sel, param);

        return box;
    }

    /**
     * Creates the 'Second Row' of GUI elements in the window.
     * 
     * @return HBox
     */
    private HBox init_rowTwo() {
        Label an = new Label("Analysis Method Description");
        Label df = new Label("Distance Function Description");
        anArea = new TextArea();
        dfArea = new TextArea();
        HBox box = new HBox(5);
        VBox amd = new VBox();
        VBox dfd = new VBox();

        anArea.prefHeightProperty().bind(this.box.heightProperty());
        anArea.prefWidthProperty().bind(this.box.widthProperty());
        dfArea.prefHeightProperty().bind(this.box.heightProperty());
        dfArea.prefWidthProperty().bind(this.box.widthProperty());
        anArea.setMinSize(100, 100);
        dfArea.setMinSize(100, 100);
        anArea.setWrapText(true);
        dfArea.setWrapText(true);
        anArea.setEditable(false);
        dfArea.setEditable(false);

        an.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));
        df.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 24));

        amd.getChildren().addAll(an, anArea);
        dfd.getChildren().addAll(df, dfArea);

        box.getChildren().addAll(amd, dfd);

        return box;
    }

    /**
     * Method for generating the selected list view bow.
     * 
     * @return ListView<AnalysisDriver>
     */
    private ListView<AnalysisDriver> init_SelectedBox() {
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());

        selList.setItems(selItems);
        selList.prefHeightProperty().bind(this.box.heightProperty());
        selList.prefWidthProperty().bind(this.box.widthProperty());
        selList.setCellFactory(param -> new ListCell<AnalysisDriver>() {
            @Override
            protected void updateItem(AnalysisDriver item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.displayName().equalsIgnoreCase("")) {
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        selList.setOnMouseClicked(e -> {
            AnalysisDriver sel = selList.getSelectionModel().getSelectedItem();
            VBox para = sel.getNewGUILayout();

            logger.info("Changing Analysis Tab Parameter Boxes");
            para.prefHeightProperty().bind(this.box.heightProperty());
            para.prefWidthProperty().bind(this.box.widthProperty());
            this.param.getChildren().removeLast();
            this.param.getChildren().add(para);

            e.consume();
        });

        return selList;
    }

    /**
     * Method for generating the analysis method selection box.
     * 
     * @return ListView<AnalysisDriver>
     */
    private ListView<AnalysisDriver> init_AnalysisMethodBox() {
        selList = new ListView<AnalysisDriver>();
        anList = new ListView<AnalysisDriver>();

        ObservableList<AnalysisDriver> anItems = FXCollections.observableArrayList(AnalysisDriverMasterList);

        anList.setItems(anItems);
        anList.setCellFactory(param -> new ListCell<AnalysisDriver>() {
            @Override
            protected void updateItem(AnalysisDriver item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.displayName().equalsIgnoreCase("")) {
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        anList.prefHeightProperty().bind(this.box.heightProperty());
        anList.prefWidthProperty().bind(this.box.widthProperty());
        anList.setOnMouseClicked(e -> {
            dfList.getSelectionModel().clearSelection();
            AnalysisDriver sel = anList.getSelectionModel().getSelectedItem();
            if (!(sel instanceof NeighborAnalysisDriver)) {
                dfList.setDisable(true);
            } else {
                dfList.setDisable(false);
            }
            anArea.setText(sel.longDescription());
            e.consume();
        });

        return anList;
    }

    /**
     * Method for generating the distance function selection box.
     * 
     * @return ListView<DistanceFunction>
     */
    private ListView<DistanceFunction> init_DistanceFunctionBox() {
        dfList = new ListView<DistanceFunction>();

        ObservableList<DistanceFunction> dfItems = FXCollections.observableArrayList(DistanceFunctionsMasterList);

        dfList.setItems(dfItems);
        dfList.setCellFactory(param -> new ListCell<DistanceFunction>() {
            @Override
            protected void updateItem(DistanceFunction item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.displayName().equalsIgnoreCase("")) {
                    setText("");
                } else {
                    setText(item.displayName());
                }
            }
        });
        dfList.prefHeightProperty().bind(this.box.heightProperty());
        dfList.prefWidthProperty().bind(this.box.widthProperty());
        dfList.setOnMouseClicked(e -> {
            dfArea.setText(dfList.getSelectionModel().getSelectedItem().longDescription());
            e.consume();
        });

        return dfList;
    }

    /**
     * Method for generating a VBox containing the buttons for de/selecting items
     * for the Selection Box.
     * 
     * @return VBox
     */
    private VBox init_rowOneButtons() {
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
        all.setTooltip(new Tooltip("Select all Analysis Drivers"));

        box.setMinSize(50, 0);

        VBox.setVgrow(region1, Priority.ALWAYS);
        VBox.setVgrow(region2, Priority.ALWAYS);
        left.setOnAction(e -> {
            if (!JAPI.getAnalysisDrivers().isEmpty()) {
                String selection = selList.getSelectionModel().getSelectedItem().displayName();
                if (selection.contains("with")) {
                    dfRemove(selList.getSelectionModel().getSelectedItem());
                } else {
                    anDeselected(selList.getSelectionModel().getSelectedItem());
                }
                selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
                selList.setItems(selItems);
                selList.refresh();
            }
            e.consume();
        });
        right.setOnAction(e -> {
            if (dfList.getSelectionModel().isEmpty()) {
                anSelected(anList.getSelectionModel().getSelectedItem());
            } else {
                dfAdd(dfList.getSelectionModel().getSelectedItem(), anList.getSelectionModel().getSelectedItem());
                dfList.getSelectionModel().clearSelection();
            }
            selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
            selList.setItems(selItems);
            selList.getSelectionModel().selectLast();
            selList.getFocusModel().focusNext();
            selList.refresh();
            // this.selList.getSelectionModel().select(this.selItems.getLast());
            e.consume();
        });
        clear.setOnAction(e -> {
            if (!JAPI.getAnalysisDrivers().isEmpty()) {
                JAPI.removeAllAnalysisDrivers();
                selItems.clear();
                selList.setItems(selItems);
                selList.refresh();
            }
            e.consume();

        });
        all.setOnAction(e -> {
            allSelected();
            e.consume();
        });

        box.getChildren().addAll(region1, right, left, all, clear, region2);
        box.setAlignment(Pos.TOP_CENTER);

        return box;

    }

    /**
     * Method for initializing the Distance Function Master List.
     */
    private void init_distanceFunctions() {
        DistanceFunctionsMasterList = new ArrayList<DistanceFunction>();
        for (int i = 0; i < DistanceFunctions.getDistanceFunctions().size(); i++) {
            DistanceFunction distanceFunction = DistanceFunctions.getDistanceFunctions().get(i);
            if (distanceFunction.showInGUI())
                DistanceFunctionsMasterList.add(distanceFunction);
        }
    }

    /**
     * Method for initializing the Analysis Driver Master List.
     */
    private void init_analysisDrivers() {
        AnalysisDriverMasterList = new ArrayList<AnalysisDriver>();
        for (int i = 0; i < AnalysisDrivers.getAnalysisDrivers().size(); i++) {
            AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDrivers().get(i);
            if (analysisDriver.showInGUI())
                AnalysisDriverMasterList.add(analysisDriver);
        }
    }

    /**
     * Method for adding an analysis driver.
     * 
     * @param obj Analysis Driver
     */
    private void anSelected(AnalysisDriver obj) {
        String method = obj.displayName();
        logger.info("Adding Analysis Method " + method);
        try {
            JAPI.addAnalysisDriver(method);
        } catch (Exception e) {
            logger.info(e.getCause());
            e.printStackTrace();
        }
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
        selList.setItems(selItems);
        selList.refresh();
    }
    /**
     * Select all Analysis Drivers.
     */
    private void allSelected() {
        logger.info("Adding All Analysis Methods");
        for (AnalysisDriver temp : AnalysisDriverMasterList) {
            try {
                JAPI.addAnalysisDriver(temp.displayName());
            } catch (Exception e) {
                logger.info(e.getCause());
                e.printStackTrace();
            }
        }
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
        selList.setItems(selItems);
        selList.refresh();
    }

    /**
     * Method for removing an Analysis Driver.
     * 
     * @param obj Analysis Driver.
     */
    private void anDeselected(AnalysisDriver obj) {
        String method = obj.displayName();
        logger.info("Removing Analysis Method " + method);
        JAPI.removeAnalysisDriver(obj);
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
        selList.setItems(selItems);
        selList.refresh();
    }
    /**
     * Add a Distance Function to the selected Analysis Function.
     * @param dis DistanceFunction
     * @param ana AnalysisDriver
     */

    private void dfAdd(DistanceFunction dis, AnalysisDriver ana) {
        AnalysisDriver and = null;
        String method = dis.displayName();
        logger.info("Adding Distance Function " + method);
        if (ana.displayName().contains("with")) {
            String[] temp = ana.displayName().replace(" with metric ", ":").split("\\:");
            try {
                and = JAPI.addAnalysisDriver(temp[0]);
            } catch (Exception e) {
                logger.info(e.getCause());
                e.printStackTrace();
            }
        } else {
            try {
                and = JAPI.addAnalysisDriver(ana.displayName());
            } catch (Exception e) {
                logger.info(e.getCause());
                e.printStackTrace();
            }
        }

        if (and instanceof NeighborAnalysisDriver) {
            // If the analysis driver that was selected requires a distance,
            // add the selected distance function.
            try {
                JAPI.addDistanceFunction(dis.displayName(), and);
            } catch (Exception e) {
                logger.info(e.getCause());
                e.printStackTrace();
            }
        } else if (and instanceof NonDistanceDependentAnalysisDriver) {
            // If the analysis driver that was selected is dependent on
            // another analysis driver being selected, add the one that
            // that is selected.
            try {
                JAPI.addAnalysisDriverAsParamToOther(method,
                        (NonDistanceDependentAnalysisDriver) and);
            } catch (Exception e) {
                logger.info(e.getCause());
                e.printStackTrace();
            }
        }
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
        selList.setItems(selItems);
        selList.refresh();
    }
    /**
     * Remove a Distance Function and its Analysis Driver.
     * 
     * @param obj AnalysisDriver
     */

    private void dfRemove(AnalysisDriver obj) {
        String method = obj.displayName();
        logger.info("Removing Distance Function " + method);
        JAPI.removeAnalysisDriver(obj);
        selItems = FXCollections.observableArrayList(JAPI.getAnalysisDrivers());
        selList.setItems(selItems);
        selList.refresh();
    }

    /**
     * Getter method for getting the built pane.
     * 
     * @return VBox
     */
    public VBox getPane() {
        build_pane();
        logger.info("Finished building Analysis Tab");
        return this.box;
    }

    /**
     * Method for adding bottom buttons to Panel
     * 
     * @param box HBox
     */
    public void setBottomButtons(HBox box) {
        bottomButtons = box;
    }
}
