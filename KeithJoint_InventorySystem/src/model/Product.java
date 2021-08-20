/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Keith Joint
 */
public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int ID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int ID, String name, double price, int stock, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public Product(){
        this.ID = 0;
        this.name = "";
        this.price = 0.00;
        this.min = 0;
        this.max = 0;
    }

   

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatedParts(ObservableList<Part> p_associatedParts){
        associatedParts = p_associatedParts;
    }
    public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
    }
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
    
    public static String productCheck(String name, String price, String inv, String min, String max){
        String errorThrow = "";
        Double doublePrice = Double.valueOf(price);
        Integer intInv = Integer.valueOf(inv);
        Integer intMin = Integer.valueOf(min);
        Integer intMax = Integer.valueOf(max);
        
        if(name.length() == 0){
            errorThrow += "Product name cannot be left blank.\n";
        }
        if(intInv < 1){
            errorThrow += "Inventory cannot be less than one.\n"; 
        }
        if(doublePrice <= 0){
            errorThrow += "Price must be greater than $0.00.\n";
        }
        if(intMin > intMax){
            errorThrow += "Max must be greater than min.\n"; 
        }
        if(intMax < intMin){
            errorThrow += " Min must be less than max.\n";
        }
        if(intInv < intMin || intInv > intMax){
            errorThrow += "Inventory must be greater than or equal to min and less than or equal to max.\n";
        }
        return errorThrow;
    }
    
    
    
}
