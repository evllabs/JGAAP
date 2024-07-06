package com.jgaap.GUI;


import java.io.IOException;

import com.jgaap.JGAAPConstants;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
/**
 * About Window Class.
 * This Class creates the Stage for the About Window and it's GUI elements.
 * 
 * @author Edward Polens
 */
public class GUI_JGAAPAboutWindow {

     private static Stage stage;

    /**
     * Builds the Window.
     */
    private static void build_stage(){
        Scene scene;
        VBox box = new VBox(5);
        HBox butBox = new HBox(5);
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        
        Button close = new Button("Close");
        close.setOnAction(e -> {
            if(e.getTarget().toString().contains("Close")){
                stage.close();
                e.consume();
            }
        });
        butBox.getChildren().addAll(region1, close, region2);

        box.getChildren().addAll(init_rowOne(),init_rowTwo(),butBox);

        stage = new Stage();
        scene = new Scene(box, 530, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("About");
        stage.setOnCloseRequest(e -> {
            stage.close();
            e.consume();
        });
    }

    /**
     * Method for building the first row of elements.
     * 
     * @return HBox
     */
    private static HBox init_rowOne(){
        HBox box = new HBox(5);
        Image evlIcon = new Image("/com/jgaap/resources/ui/EVL_Icon_duq.png");
        Image jgaapIcon = new Image("/com/jgaap/resources/ui/jgaap-icon.png");
        ImageView imageView1 = new ImageView(evlIcon);
        ImageView imageView2 = new ImageView(jgaapIcon);
        imageView1.setFitHeight(100);
        imageView1.setFitWidth(120);
        imageView1.setSmooth(true);
        imageView2.setFitHeight(100);
        imageView2.setFitWidth(120);
        imageView2.setSmooth(true);
        Label aboutLabel = new Label("JGAAP");
        aboutLabel.setFont(Font.font("Microsoft Sans Serif", FontWeight.BOLD, 46));
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);

        box.getChildren().addAll(imageView1, region1,aboutLabel,region2, imageView2);

        return box;
    }

    /**
     * Method for building the second row of elements.
     * 
     * @return VBox
     */
    private static VBox init_rowTwo(){
        HBox Labelbox = new HBox(5);
        HBox linkBox1 = new HBox(5);
        HBox linkBox2 = new HBox(5);
        HBox rightsBox = new HBox(5);
        VBox box = new VBox(5);
        Label lblNewLabel = new Label("JGAAP "+JGAAPConstants.VERSION+", The Java Graphical Authorship Attribution Program\n"
        +"\nDeveloped by the EVL lab (Evaluating Variation in Language Labratory) and"
        +"\nReleased by Dr. Patrick Juola under the AGPL v3.0");
        Label lblNewLabel_1 = new Label("EVLLabs Website");
        Label lblNewLabel_2 = new Label("JGAAP Website");
        Label lblNewLabel_3 = new Label("\u00A9"+JGAAPConstants.YEAR+" EVL lab");
        lblNewLabel.setMaxWidth(Double.MAX_VALUE);
        lblNewLabel.setAlignment(Pos.CENTER);
        lblNewLabel_1.setTextFill(Color.color(0,0,1));
        lblNewLabel_2.setTextFill(Color.color(0,0,1));
        Region region1 = new Region();
        Region region2 = new Region();
        Region region3 = new Region();
        Region region4 = new Region();
        Region region5 = new Region();
        Region region6 = new Region();
        Region region7 = new Region();
        Region region8 = new Region();
        lblNewLabel_1.setOnMouseClicked(e -> {
            browseToURL("https://www.evllabs.com");
        });
        lblNewLabel_2.setOnMouseClicked(e -> {
            browseToURL("https://www.jgaap.com");
        });
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setHgrow(region3, Priority.ALWAYS);
        HBox.setHgrow(region4, Priority.ALWAYS);
        HBox.setHgrow(region5, Priority.ALWAYS);
        HBox.setHgrow(region6, Priority.ALWAYS);
        HBox.setHgrow(region7, Priority.ALWAYS);
        HBox.setHgrow(region8, Priority.ALWAYS);
        Labelbox.getChildren().addAll(region1,lblNewLabel,region2);
        linkBox1.getChildren().addAll(region3,lblNewLabel_1,region4);
        linkBox2.getChildren().addAll(region5, lblNewLabel_2, region6);
        rightsBox.getChildren().addAll(region7, lblNewLabel_3, region8);
        box.getChildren().addAll(Labelbox,linkBox1,linkBox2,rightsBox);
        return box;
    }

    /**
     * Method to open a browser to the given URL
     * 
     * @param url String
     * @return boolean
     */
    public static boolean browseToURL(String url) {
		boolean succees = false;
		try {
			java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
			succees = true;
		} catch (IOException e) {
			e.printStackTrace();
                  Alert error = new Alert(AlertType.ERROR, e.getMessage());
                  error.showAndWait()
                        .filter(response -> response == ButtonType.OK)
                        .ifPresent(response -> error.close());
		}
		return succees;
	}
    
    /**
     * Controls showing or closing (Hiding) the window.
     */
    public static void show(){
        if(stage == null){
            build_stage();
            stage.show();
        } else {
            stage.show();
        }
    }
}
