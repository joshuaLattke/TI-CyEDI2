package com.integradora;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

public class BomberApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 560);
        Image icon = new Image("/Icon.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.setTitle("BomberMan");
        primaryStage.setResizable(false);
        primaryStage.show();


        //add prueba = new Rectangle(50,50,50,50);
        // Obtener la raíz (Group) de la escena existente desde la clase Main
        //Group roota = scene.getRoot(); // Suponiendo que tienes una referencia a la escena desde Main

        // Agregar el rectángulo al Group (raíz de la escena)
        //roota.getChildren().add(rectangulo);
    }




    public static void main(String[] args) {
        launch(args);
    }
}

