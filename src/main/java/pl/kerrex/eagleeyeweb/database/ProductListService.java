/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.kerrex.eagleeyeweb.database;

import pl.kerrex.eagleeyeweb.logic.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomek
 */
public class ProductListService {
    private final DBConnector db;
    private final List<Product> productList;

    public ProductListService() {
        db = DBConnector.getInstance();
        productList = new ArrayList<>();
    }
    
    /**
     *
     * @return
     */
    public List<Product> createAllProductList() {
        ResultSet rs = null;
        try {
            String name, EAN;
            int quantity;
            
            rs = db.getAllBoughtProducts();
            
            rs.next();
            while(!rs.isAfterLast()) {
                name = rs.getString(2);
                EAN = rs.getString(3);
                quantity = rs.getInt(4);
                productList.add(new Product(name, EAN, quantity));
                rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductListService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(ProductListService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return productList;
    }

    public List<Product> createProductList(String fromDateString, String toDateString, String customers) {
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fromDate = sdf.parse(fromDateString);
            Date toDate = sdf.parse(toDateString);
            String name, EAN;
            int quantity;
            rs = db.getBoughtProducts(fromDate, toDate, customers.substring(1, customers.length()-1));
            rs.next();
            while (!rs.isAfterLast()) {
                name = rs.getString(1);
                EAN = rs.getString(2);
                quantity = rs.getInt(3);
                productList.add(new Product(name, EAN, quantity));
                rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return productList;
    }
    
    
}
