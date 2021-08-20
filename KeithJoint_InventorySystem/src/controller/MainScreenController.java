/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Inventory.lookupPart;
import static model.Inventory.lookupProduct;
import model.Part;
import model.Product;

/**
 *
 * @author Keith Joint
 */
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;

    @FXML
    private TextField productSearchTxt;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIDCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPricePerUnitCol;
    
    private static Part selectedPart;
    private static Product selectedProduct;
    
    private static int partToModifyIndex = -1;
    private static int productToModifyIndex = -1;
    
    public static int selectedPartIndex(){
        return partToModifyIndex;
    }
    
    public static int selectedProductIndex(){
        return productToModifyIndex;
    } 
    
    public static Part getSelectedPart(){
        
        return selectedPart;
    }
    
    public static Product getSelectedProduct(){
        return selectedProduct;
    }
    
    public void setSelectedPart(Part part){
        MainScreenController.selectedPart = part;
    }
    
    public void setSelectedProduct(Product product){
        MainScreenController.selectedProduct = product;
    }
    
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        
      stage = (Stage)((Button)event.getSource()).getScene().getWindow();
      scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
      stage.setScene(new Scene(scene));
      stage.show();
      
    }

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        
      stage = (Stage)((Button)event.getSource()).getScene().getWindow();
      scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
      stage.setScene(new Scene(scene));
      stage.show();  

    }

    @FXML
    void onActionDeletePart(ActionEvent event) {
        
        if(partsTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Part not selected");
            alert.setHeaderText("Please select a part from the list"); 
            alert.showAndWait();
        }
        else{
        
        
           selectedPart = partsTableView.getSelectionModel().getSelectedItem();
           if(selectedPart != null){
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part, do you want to continue?");
               Optional<ButtonType> result = alert.showAndWait();
               if(result.isPresent() && result.get() == ButtonType.OK){
                   Inventory.deletePart(selectedPart);
                   partsTableView.setItems(getAllParts());
            }
                

            
            
           }
        }
        
    }

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Product not selected");
            alert.setHeaderText("Please select a product from the list"); 
            alert.showAndWait();
         }
        else{
        
            Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
            if(selectedProduct != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the product, are you sure you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    Inventory.deleteProduct(selectedProduct);
                    productTableView.setItems(getAllProducts());
                }
            }
        }

    }

    @FXML
    void onActionExit(ActionEvent event) {
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to exit ?");
       Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
       }
    }

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        Part partIndex = partsTableView.getSelectionModel().getSelectedItem();
        partToModifyIndex = getAllParts().indexOf(partIndex);
        
     if(partsTableView.getSelectionModel().getSelectedItem() == null){
         Alert alert = new Alert(Alert.AlertType.WARNING);
         alert.initModality(Modality.APPLICATION_MODAL);
         alert.setTitle("Part not selected");
         alert.setHeaderText("Please select a part from the list"); 
         alert.showAndWait();
     }
     
     else{   
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ModifyPart.fxml"));
        Parent scene = loader.load();

        ModifyPartController MPCController = loader.getController();
        MPCController.sendPart(partsTableView.getSelectionModel().getSelectedItem());

         stage = (Stage)((Button)event.getSource()).getScene().getWindow();
         stage.setScene(new Scene(scene));
         stage.show();

       }
    }

    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        
        Product productIndex = productTableView.getSelectionModel().getSelectedItem();
        productToModifyIndex = getAllProducts().indexOf(productIndex);
        
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Product not selected");
            alert.setHeaderText("Please select a product from the list"); 
            alert.showAndWait();
         }
        else{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProduct.fxml"));
            Parent scene = loader.load();
            
            ModifyProductController MPRController = loader.getController();
            MPRController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
            
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(scene));
            stage.show();
            
         }
    }

    @FXML
    void onActionSearchParts(ActionEvent event) {
        String parts = partSearchTxt.getText();
        ObservableList<Part> list = Inventory.lookupPart(parts);
        
        if(list.isEmpty()){
          try{ 
            int partID = Integer.parseInt(parts);
            Part p = lookupPart(partID);
            if(p != null)
                list.add(p);
            
             }
          catch(NumberFormatException e){
              //ignore
          }
          
        }
        
        partsTableView.setItems(list);
        partSearchTxt.setText("");
        
    }
    @FXML
    void onActionSearchProducts(ActionEvent event) {
        String products = productSearchTxt.getText();
        ObservableList<Product> list = Inventory.lookupProduct(products);
        
        if(list.isEmpty()){
            try{
                int productID =  Integer.parseInt(products);
                Product p = lookupProduct(productID);
                if(p != null)
                    list.add(p);
            }
            catch(NumberFormatException e){
                // ignore
            }
        }
        productTableView.setItems(list);
        productSearchTxt.setText("");

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        productTableView.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        
    }    
    
}
