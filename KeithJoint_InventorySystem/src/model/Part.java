/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;



/**
 *
 * @author Keith Joint
 */
public abstract class Part {
    
    private int ID;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    

    public Part(int ID, String name, double price, int stock, int min, int max) {
        this.ID = ID;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    
    public Part(){
        this.ID = 0;
        this.name = "";
        this.price = 0.00;
        this.stock = 0;
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
    
    public static String partCheck(String name, String inv, String min, String max, String price){
        String errorThrow = "";
        Integer intInv = Integer.parseInt(inv);
        Integer intMin = Integer.parseInt(min);
        Integer intMax = Integer.parseInt(max);
        Double doublePrice = Double.parseDouble(price);
        
        if(name.length() == 0){
            errorThrow += "Part name cannot be left blank.\n";
        }
        if(intMin < 1){
            errorThrow += "Inventory cannot be left blank.\n";
        }
        if(intMax < intMin){
            errorThrow += "Max must be greater than min.\n";
        }
        if(intInv < intMin || intInv > intMax){
            errorThrow += "Inventory must be at least the min value and at most the max value.\n";
        }
        if(doublePrice <= 0){
            errorThrow += "Price must be greater than $0.00.\n";
        }
        return errorThrow;
    }
    
        
        
        
        
        
    
    
}
