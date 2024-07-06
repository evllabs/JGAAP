package com.jgaap.GUI;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.ByteArrayOutputStream;

import org.apache.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;


public class GUI_LogWindow {

    private static Stage stage;
    private static TextArea area;
    private static Thread printer;
    private static Logger logger;
    private static ByteArrayOutputStream outputStream;

    public GUI_LogWindow(){
        logger = Logger.getLogger(GUI_LogWindow.class);
        outputStream = (ByteArrayOutputStream) IoBuilder
            .forLogger((org.apache.logging.log4j.Logger) logger)
            .buildOutputStream();
        stage = new Stage();
        area = new TextArea();
        stage.setScene(build_scene());
        stage.setTitle("Log Messages");
        stage.setOnCloseRequest(e -> {
            hideStage();
        });
    }
    public Scene build_scene(){
        VBox box = new VBox(5);
        Scene scene;
        area.setEditable(false);

        area.prefHeightProperty().bind(box.prefHeightProperty());
        area.prefWidthProperty().bind(box.prefWidthProperty());

        printer = new Thread(() -> {
            while(!Thread.currentThread().isInterrupted()){
                area.setText(outputStream.toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("Thread stopped");
        });

        VBox.setVgrow(area, Priority.ALWAYS);
        box.getChildren().addAll(area);
        scene = new Scene(box, 500, 500);
        return scene;
    }
    private void updateWindow(boolean status){
        if(status){
            printer.start();
        } else {
            while(printer.isInterrupted()){
                if(!printer.isInterrupted()){
                    printer.interrupt();
                    try {
                        printer.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public void showStage(){
        updateWindow(true);
        stage.show();
    }
    public void hideStage(){
        updateWindow(false);
        stage.hide();
    }
    
}
