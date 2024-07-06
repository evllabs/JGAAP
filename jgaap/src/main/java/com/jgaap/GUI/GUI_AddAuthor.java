package com.jgaap.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.API;
import com.jgaap.util.Document;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * GUI_AddAuthor class is for building a Window to add an Author to the program.
 * 
 * @author Edward Polens
 */
public class GUI_AddAuthor {

    private static TableView<Document> table;
    private static ArrayList<Document> docs;
    private static Logger logger;
    private static API JAPI;
    private static TextField auth;
    private static String selAuth;
    private Stage stage;

    /**
     * Initial constructor for the class.
     */
    public GUI_AddAuthor() {
        logger = Logger.getLogger(GUI_AddAuthor.class);
        logger.info("Author Adding constructor called.");
        docs = new ArrayList<Document>();
        this.stage = new Stage();
        JAPI = API.getInstance();
        selAuth = "";
        stage.setTitle("Add Author");
        stage.setScene(init_scene());
        logger.info("Finished building Adding Author Window.");

    }
    /**
     * Constructor for the class to edit an author.
     * 
     * @param author String
     */
    public GUI_AddAuthor(String author) {
        logger = Logger.getLogger(GUI_AddAuthor.class);
        logger.info("Author Editing constructor called.");
        docs = new ArrayList<Document>();
        this.stage = new Stage();
        JAPI = API.getInstance();
        stage.setTitle("Add Author");
        selAuth = author;
        stage.setScene(init_scene());
        logger.info("Finished building Edit Author Window.");
    }
    /**
     * Method to build the Scene for the Stage.
     * 
     * @return Scene
     */
    private Scene init_scene() {
        logger.info("Building Author Window.");
        VBox box = new VBox();
        box.getChildren().addAll(init_authorBox(), init_authorTable(), init_bottomButtons());
        box.setPadding(new Insets(5));
        Scene scene = new Scene(box,500,300);
        return scene;

    }
    /**
     * Method to initialize the Author name text box.
     * 
     * @return HBox
     */
    private HBox init_authorBox() {
        HBox box = new HBox(5);
        Label authl = new Label("Author");
        auth = new TextField();
        auth.setOnAction(e -> {
            updateItemView();
            e.consume();
        });
        box.getChildren().addAll(authl, auth);
        return box;
    }
    /**
     * Method to initialize the Documents to be added to the Author.
     * 
     * @return VBox
     */
    private VBox init_authorTable() {
        VBox box = new VBox(5);
        HBox butBox = new HBox(5);
        Button add = new Button("Add Document");
        Button rem = new Button("Remove Document");
        table = new TableView<Document>();
        TableColumn<Document, String> column1 = new TableColumn<Document, String>("Title");
        TableColumn<Document, String> column2 = new TableColumn<Document, String>("File Path");
        column1.setCellValueFactory(new PropertyValueFactory<Document, String>("Title"));
        column2.setCellValueFactory(new PropertyValueFactory<Document, String>("FilePath"));
        column1.prefWidthProperty().bind(table.widthProperty().divide(2));
        column2.prefWidthProperty().bind(table.widthProperty().divide(2));
        table.getColumns().add(column1);
        table.getColumns().add(column2);
        table.prefHeightProperty().bind(this.stage.heightProperty());
        table.prefWidthProperty().bind(this.stage.widthProperty());
        if(selAuth.isEmpty()){
            table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(auth.getText().trim())));
        } else {
            for(Document i: JAPI.getDocumentsByAuthor(selAuth)){
                docs.add(i);
            }
            auth.setText(selAuth);
            table.setItems(FXCollections.observableArrayList(docs));
        }
        // ===============================================================================
        add.setOnAction(e -> {
            FileChooser FileChoser = new FileChooser();
            List<File> choice = FileChoser.showOpenMultipleDialog(this.stage);
            for (File file : choice) {
                try {
                    String filepath = file.getCanonicalPath();
                    String[] Split = filepath.split("[\\\\[\\/]]");
                    String Title = Split[Split.length - 1];
                    Document temp = new Document(filepath, auth.getText().trim(), Title);
                    docs.add(temp);
                    table.setItems(FXCollections.observableArrayList(docs));
                    table.refresh();
                } catch (Exception ex) {
                    logger.error(ex.getCause(), ex);
                    ex.printStackTrace();
                }
            }
            e.consume();
        });
        // ===============================================================================
        // ===============================================================================
        rem.setOnAction(e -> {
            TableViewSelectionModel<Document> selected = table.getSelectionModel();
            List<Document> docs = new ArrayList<Document>();
            docs.addAll(selected.getSelectedItems());
            for (Document doc : docs) {
                docs.remove(doc);
            }
            table.setItems(FXCollections.observableArrayList(docs));
            table.refresh();
            e.consume();
        });
        // ===============================================================================
        butBox.getChildren().addAll(add, rem);
        box.getChildren().addAll(table, butBox);
        return box;

    }
    /**
     * Build the bottom buttons on the Panel.
     * 
     * @return HBox
     */
    private HBox init_bottomButtons() {
        HBox box = new HBox(5);
        Button ok = new Button("OK");
        Button can = new Button("Cancel");
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        ok.setTooltip(new Tooltip("Confirm changes."));
        can.setTooltip(new Tooltip("Remove all changes."));
        // ===============================================================================
        ok.setOnAction(e -> {
            if (!docs.isEmpty()) {
                if(selAuth.isEmpty()){
                    addDoc(docs);
                } else if(!selAuth.equalsIgnoreCase(auth.getText())) {
                    updateAuthor(docs);
                }
            }
            GUI_DocTab.updateAuthorTree();
            stage.close();
            e.consume();
        });
        // ===============================================================================
        // ===============================================================================
        can.setOnAction(e -> {
            auth.setText("");
            selAuth = "";
            docs.clear();
            stage.close();
            e.consume();
        });
        // ===============================================================================
        box.getChildren().addAll(region1,ok, can);

        return box;
    }
    /**
     * Update the Document List View box.
     */
    private void updateItemView() {
        table.setItems(FXCollections.observableArrayList(JAPI.getDocumentsByAuthor(auth.getText().trim())));
        table.refresh();
    }
    /**
     * Add a document.
     * 
     * @param doc ArrayList<Document>
     */
    private void addDoc(ArrayList<Document> doc){
        for(Document i : doc){
            JAPI.addDocument(i);
        }
    }
    /**
     * Updates the author tree.
     * 
     * @param doc ArrayList<Document>
     */
    private void updateAuthor(ArrayList<Document> doc){
        String temp = auth.getText().trim();
        for(Document i : doc){
            JAPI.removeDocument(i);
            i.setAuthor(temp);
            JAPI.addDocument(i);
        }
    }
    /**
     * Get the constructed stage (Window).
     * 
     * @return Stage
     */
    public Stage getStage() {
        return this.stage;
    }

}
