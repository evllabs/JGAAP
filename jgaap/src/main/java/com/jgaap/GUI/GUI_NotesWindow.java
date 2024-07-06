package com.jgaap.GUI;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Notes Window Class.
 * This Class creates the Stage for the Notes Window and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_NotesWindow{

    private static Stage stage;
    private static Button notes;
    private static TextArea area;
    private static String text;

    /**
     * Constructor for the class.
     */
    public GUI_NotesWindow(){
        stage = new Stage();
        stage.setTitle("Notes");
        stage.hide();
        build_stage();
        stage.setOnCloseRequest(e -> {
            area.setText(text);
            stage.close();
            e.consume();
        });
    }
    /**
     * Builds the Window and its elements.
     */
    private void build_stage(){
        Scene scene;
        VBox box = new VBox(5);
        area = new TextArea();
        notes = new Button("Notes");
        notes.setTooltip(new Tooltip("Bring up the Notes Window."));
        text = "";

        scene = new Scene(box, 600, 600);

        area.prefHeightProperty().bind(scene.heightProperty());
        area.prefWidthProperty().bind(scene.widthProperty());
        notes.setOnAction(e -> {
            if(e.getTarget().toString().contains("Notes")){
                if(!stage.isShowing()){
                    stage.show();
                    e.consume();
                } else {
                    e.consume();
                }
            }
        });

        box.getChildren().addAll(area,init_bottomButtons());


        stage.setScene(scene);
    }
    /**
     * Builds the bottom row buttons.
     * 
     * @return HBox
     */
    private HBox init_bottomButtons(){
        HBox box = new HBox(5);
        Button ok = new Button("OK");
        Button cancel = new Button("Cancel");
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);

        ok.setTooltip(new Tooltip("Save Changes."));
        cancel.setTooltip(new Tooltip("Remove changes."));
        ok.setOnAction(e -> {
            if(e.getTarget().toString().contains("OK")){
                text = area.getText();
                e.consume();
            }
        });
        cancel.setOnAction(e -> {
            if(e.getTarget().toString().contains("Cancel")){
                area.setText(text);
                stage.hide();
                e.consume();
            }
        });

        box.getChildren().addAll(region,ok,cancel);
        
        return box;

    }
    /**
     * Show the Window.
     */
    public void show(){
        stage.show();
    }
    /**
     * Hide the Window.
     */
    public void hide(){
        stage.hide();
    }
    /**
     * Close (hide) the Window.
     */
    public void close(){
        stage.close();
    }
    /**
     * Getter for getting the notes typed in the Text Area.
     * 
     * @return String
     */
    public String getNotes(){
        return text;
    }
    /**
     * Getter for getting the "Notes" button for the GUI windows.
     * 
     * @return Button
     */
    public Button getButton(){
        return notes;
    }
}