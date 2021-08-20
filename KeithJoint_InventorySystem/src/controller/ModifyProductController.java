/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.MainScreenController.selectedProductIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import model.Inventory;
import static model.Inventory.lookupPart;
import model.Part;
import model.Product;

/**
 * FXML Controller class
 *
 * @author Keith Joint
 */
public class ModifyProductController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TextField productIDTxt;

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField productInvTxt;

    @FXML
    private TextField productPriceTxt;

    @FXML
    private TextField productMaxTxt;

    @FXML
    private TextField productMinTxt;

    @FXML
    private TextField partSearchTxt;

    @FXML
    private TableView<Part> partsTableview;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPricePerUnitCol;

    @FXML
    private TableView<Part> productPartTableView;

    @FXML
    private TableColumn<Part, Integer> ProductPartIDCol;

    @FXML
    private TableColumn<Part, String> productPartNameCol;

    @FXML
    private TableColumn<Part, Integer> productPartInventoryCol;

    @FXML
    private TableColumn<Part, Double> productPartPricePerUnitCol;
    
    private ObservableList<Part> p_associatedParts = FXCollections.observableArrayList();
    private static Part selectedProductPart;
    private String errorMessage = new String();
    

    @FXML
    void onActionAddPartToProduct(ActionEvent event) {
        Part p = (partsTableview.getSelectionModel().getSelectedItem());
       if(p == null){
           return;
       }
       
      p_associatedParts.add(p);
       // Product.addAssociatedPart(partsTableview.getSelectionModel().getSelectedItem());
       // productPartTableView.setItems(Product.getAllAssociatedParts());
    }

    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Any changes will not be saved. Do you wish to continue?");
       Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();   
       }
        
      

    }

    @FXML
    void onActionDeletePartFromProduct(ActionEvent event) {
        selectedProductPart = productPartTableView.getSelectionModel().getSelectedItem();
         if(selectedProductPart != null){
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will delete the part, do you want to continue?");
               Optional<ButtonType> result = alert.showAndWait();
               if(result.isPresent() && result.get() == ButtonType.OK){
                  p_associatedParts.remove(selectedProductPart);
                   
               }
            }

    }

    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        String name = productNameTxt.getText();
        String price = productPriceTxt.getText();
        String inv = productInvTxt.getText();
        String min = productMinTxt.getText();
        String max = productMaxTxt.getText();
        
        try{
            
            errorMessage = Product.productCheck(name, price, inv, min, max);
            if(errorMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("There was an error saving the product.");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            }
            else{
                Product product = new Product();
                product.setID(Integer.parseInt(productIDTxt.getText()));
                product.setName(productNameTxt.getText());
                product.setPrice(Double.parseDouble(productPriceTxt.getText()));
                product.setStock(Integer.parseInt(productInvTxt.getText()));
                product.setMin(Integer.parseInt(productMinTxt.getText()));
                product.setMax(Integer.parseInt(productMaxTxt.getText()));
                product.addAssociatedParts(p_associatedParts);
                Inventory.updateProduct(selectedProductIndex(), product);

                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error saving the product");
            alert.setContentText("Cannot leave blank fields.");
            alert.showAndWait();
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
        
        partsTableview.setItems(list);
        partSearchTxt.setText("");

    }
    public void sendProduct(Product product){
        productIDTxt.setText(String.valueOf(product.getID()));
        productNameTxt.setText(product.getName());
        productInvTxt.setText(String.valueOf(product.getStock()));
        productPriceTxt.setText(String.valueOf(product.getPrice()));
        productMaxTxt.setText(String.valueOf(product.getMax()));
        productMinTxt.setText(String.valueOf(product.getMin()));
        p_associatedParts.addAll(product.getAllAssociatedParts());
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partsTableview.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        
        productPartTableView.setItems(p_associatedParts);
        ProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPartPricePerUnitCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        
        
    }    

    
    
}
