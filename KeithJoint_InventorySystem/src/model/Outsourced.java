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
public class Outsourced extends Part{
    
    private String companyName;
    
    public Outsourced(int ID, String name, double price, int stock, int min, int max, String companyName){
        super(ID, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    public Outsourced(){
        super();
        this.companyName = "";
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    
    
}
