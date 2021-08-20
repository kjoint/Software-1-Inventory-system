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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import static model.Inventory.getPartIDCount;
import model.Outsourced;
import model.Part;

/**
 * FXML Controller class
 *
 * @author Keith Joint
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    
    @FXML
    private RadioButton inHouseBtn;

    @FXML
    private ToggleGroup partType;

    @FXML
    private RadioButton outsourcedBtn;

    @FXML
    private TextField partIDTxt;


    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField companyNameTxt;
    
    
    @FXML
    private Label dynLabel;
    @FXML
    private TextField partNameTxt;
    @FXML
    private TextField partInventoryTxt;
    @FXML
    private TextField partPriceTxt;
    
    private String errorMessage = new String();
    
    
    
    void partTypeBtn(){
       if(this.partType.getSelectedToggle().equals(this.inHouseBtn)){
           this.dynLabel.setText("Machine ID");
       } 
       else{
           this.dynLabel.setText("Company name");
       }
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
    void onActionSavePart(ActionEvent event) throws IOException {
        
        String ID = partIDTxt.getText();
        String name = partNameTxt.getText();
        String price = partPriceTxt.getText();
        String stock = partInventoryTxt.getText();
        String min = partMinTxt.getText();
        String max = partMaxTxt.getText();
        String machineID = companyNameTxt.getText();
        
        try{
            
            errorMessage = Part.partCheck(name, stock, min, max, price);
            if(errorMessage.length() > 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("There was an error saving the part");
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            }
            else{
                if(this.partType.getSelectedToggle().equals(this.inHouseBtn)){
                    InHouse part = new InHouse();
                    part.setID(Integer.parseInt(ID));
                    part.setName(name);
                    part.setPrice(Double.parseDouble(price));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    part.setStock(Integer.parseInt(stock));
                    part.setMachineID(Integer.parseInt(machineID));
                    Inventory.addPart(part);

                }
                else{
                    Outsourced part = new Outsourced();
                    part.setID(Integer.parseInt(ID));
                    part.setName(name);
                    part.setPrice(Double.parseDouble(price));
                    part.setMin(Integer.parseInt(min));
                    part.setMax(Integer.parseInt(max));
                    part.setStock(Integer.parseInt(stock));
                    part.setCompanyName(machineID);
                    Inventory.addPart(part);
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error saving the part");
            alert.setContentText("Cannot leave blank fields ");
            alert.showAndWait();
        }
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partIDTxt.setText(String.valueOf(Inventory.getPartIDCount()));
        
    }    

    @FXML
    private void onInHouse(ActionEvent event) {
       partTypeBtn(); 
    }

    @FXML
    private void onOutsourced(ActionEvent event) {
        partTypeBtn();
    }
    
    
        
    

   
   
}
