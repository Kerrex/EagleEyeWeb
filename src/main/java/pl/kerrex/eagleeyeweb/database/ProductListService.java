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
    //private final List<Product> productList;

    public ProductListService() {
        db = DBConnector.getInstance();
    }
    
    /**
     *
     * @return
     */
    public List<Product> createAllProductList() {
        ResultSet rs = null;
        try {
            rs = db.getAllBoughtProducts();
            return fillProductList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(ProductListService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Product> createProductList(String fromDateString, String toDateString, String customers) {
        ResultSet rs = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date fromDate = sdf.parse(fromDateString);
            Date toDate = sdf.parse(toDateString);
            if (customers.equals("null")) customers = "SELECT idKlient FROM eagleeye.Klient";
            else customers = customers.substring(1, customers.length() - 1);
            rs = db.getBoughtProducts(fromDate, toDate, customers);
            return fillProductList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Product> fillProductList(ResultSet rs) throws SQLException {
        List<Product> productList = new ArrayList<>();
        rs.next();
        while (!rs.isAfterLast()) {
            String name = rs.getString(1);
            String EAN = rs.getString(2);
            int quantity = rs.getInt(3);
            productList.add(new Product(name, EAN, quantity));
            rs.next();
        }
        rs.close();
        return productList;
    }

}
