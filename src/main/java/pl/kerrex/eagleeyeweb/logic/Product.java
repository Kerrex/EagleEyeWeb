/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kerrex.eagleeyeweb.logic;

/**
 *
 * @author tomek
 */
public class Product {
    private String name;
    private String EAN;
    private int quantity;

    public Product(String name, String EAN, int quantity) {
        this.name = name;
        this.EAN = EAN;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
