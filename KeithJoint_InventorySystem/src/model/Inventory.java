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
public class Inventory {
    private static int partIDCount = 98;
    private static int productIDCount = 999;
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    
    public static void addPart(Part newPart){
        
        
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        
        allProducts.add(newProduct);
    }
    
    public static Part lookupPart(int partID){
        for(Part p: allParts){
            if(p.getID() == partID){
               return p; 
            }
        }
        return null;
    }
    
    public static Product lookupProduct(int productID){
       for(Product p : allProducts){
           if(p.getID() == productID){
               return p;
           }
       }
       return null;
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        
       ObservableList<Part> list = FXCollections.observableArrayList();
       for(Part p: allParts){
           if(p.getName().contains(partName)){
               list.add(p);
              
           }
       }
        return list; 
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        
        ObservableList<Product> list = FXCollections.observableArrayList();
        for(Product p: allProducts){
            if(p.getName().contains(productName)){
            list.add(p);
           }
        }
        return list;
      
    }
    
    public static void updatePart(int index, Part selectedPart){
         allParts.set(index, selectedPart);
    }
    
    public static void updateProduct(int index, Product selectedProduct){
         allProducts.set(index, selectedProduct);
    }
    
    public static Boolean deletePart(Part selectedPart){
       return allParts.remove(selectedPart);
        
    }
    
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
        
    } 
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    
    public static int getPartIDCount(){
        partIDCount = partIDCount + 2;
        return partIDCount;
    }
    
    public static int getProductIDCount(){
        productIDCount = productIDCount + 2;
        return productIDCount;
    }
   
    
    
}
