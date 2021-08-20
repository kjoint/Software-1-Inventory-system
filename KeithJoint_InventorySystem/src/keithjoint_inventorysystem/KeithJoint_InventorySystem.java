/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keithjoint_inventorysystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import model.Product;

/**
 *
 * @author Keith Joint
 */
public class KeithJoint_InventorySystem extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InHouse part1 = new InHouse(1, "Hub", 23.00, 5, 2, 10, 602011);
        InHouse part2 = new InHouse(2, "Sprocket", 52.00, 3, 1, 5, 602012);
        Outsourced part3 = new Outsourced(3, "Shocks", 250.00, 5, 2, 10, "Rock Shox");
        Product product1 = new Product(100, "Trek FX 9", 3200.00, 1,0,3);
        Product product2 = new Product(101, "Canondale M Frame", 2500.00, 1, 0, 3);
        
        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        
       
       launch (args);
    }
    
}
